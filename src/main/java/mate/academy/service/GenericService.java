package mate.academy.service;

import java.util.List;

public interface GenericService<T> {
    T add(T t);

    T get(Long id);

    List<T> getAll();
}
