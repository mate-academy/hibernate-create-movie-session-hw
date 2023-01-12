package mate.academy.service;

public interface GenericService<T> {
    T add(T t);

    T get(Long id);
}
