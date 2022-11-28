package mate.academy.service;

public interface GeneralService<T> {
    T add(T object);

    T get(Long id);
}
