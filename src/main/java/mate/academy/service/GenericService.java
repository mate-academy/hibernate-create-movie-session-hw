package mate.academy.service;

public interface GenericService<T> {
    T add(T entity);

    T get(Long id);
}
