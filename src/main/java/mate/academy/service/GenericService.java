package mate.academy.service;

import java.util.List;

public interface GenericService<T> {
    T add(T element);

    T get(Long id);

    List<T> getAll();
}
