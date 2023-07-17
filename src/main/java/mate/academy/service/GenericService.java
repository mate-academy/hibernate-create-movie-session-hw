package mate.academy.service;

import java.util.List;

public interface GenericService<T> {
    T add(T entity);

    T get(Long id);

    List<T> getAll();
}
