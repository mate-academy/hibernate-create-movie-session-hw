package mate.academy.dao;

import java.util.Optional;

public interface DataAccess<T> {
    T add(T entity);

    Optional<T> get(Long id);
}
