package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    T create(T entity);

    Optional<T> get(Long id);

    List<T> getAll();

    void remove(T entity);
}
