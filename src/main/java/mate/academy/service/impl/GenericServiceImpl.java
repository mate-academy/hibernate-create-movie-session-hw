package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.GenericDao;
import mate.academy.service.GenericService;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
    @Override
    public T add(T t) {
        return getDao().add(t);
    }

    @Override
    public T get(Long id) {
        return getDao().get(id).get();
    }

    @Override
    public List<T> getAll() {
        return getDao().getAll();
    }

    protected abstract GenericDao<T> getDao();
}
