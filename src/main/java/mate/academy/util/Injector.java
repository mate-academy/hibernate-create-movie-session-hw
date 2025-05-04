package mate.academy.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;

public class Injector {
    private static final Map<String, Injector> injectors = new HashMap<>();
    private final Map<Class<?>, Object> instanceOfClasses = new HashMap<>();
    private final List<Class<?>> classes = new ArrayList<>();

    private Injector(String mainPackageName) {
        try {
            classes.addAll(getClasses(mainPackageName));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't get information about all classes", e);
        }
    }

    public static Injector getInstance(String mainPackageName) {
        return injectors.computeIfAbsent(mainPackageName, Injector::new);
    }

    public Object getInstance(Class<?> certainInterface) {
        Class<?> clazz = findClassExtendingInterface(certainInterface);
        Object instance = getNewInstance(clazz);
        injectFields(instance);
        return instance;
    }

    private Class<?> findClassExtendingInterface(Class<?> certainInterface) {
        return classes.stream()
                .filter(clazz -> {
                    for (Class<?> iface : clazz.getInterfaces()) {
                        if (iface.equals(certainInterface)
                                && (clazz.isAnnotationPresent(Service.class)
                                || clazz.isAnnotationPresent(Dao.class))) {
                            return true;
                        }
                    }
                    return false;
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find class which implements "
                        + certainInterface.getName()
                        + " interface and has valid annotation (Dao or Service)"));
    }

    private Object getNewInstance(Class<?> clazz) {
        return instanceOfClasses.computeIfAbsent(clazz, this::createInstance);
    }

    private Object createInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't create object of the class "
                    + clazz.getName(), e);
        }
    }

    private void injectFields(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object fieldInstance = getInstance(field.getType());
                setField(instance, field, fieldInstance);
            }
        }
    }

    private void setField(Object instance, Field field, Object fieldInstance) {
        try {
            field.setAccessible(true);
            field.set(instance, fieldInstance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't set value to field "
                    + field.getName(), e);
        }
    }

    private static List<Class<?>> getClasses(
            String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new RuntimeException("Class loader is null");
        }
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(
            File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName
                            + "."
                            + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName
                            + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }
}
