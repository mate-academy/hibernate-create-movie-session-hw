package mate.academy.dao;

import java.util.Optional;

public interface GenericDao<T, I> {
    T add(T entity);

    Optional<T> get(I id);
}
