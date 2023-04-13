package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.MovieSession;
import org.hibernate.SessionFactory;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private final SessionFactory sessionFactory =
            (SessionFactory) injector.getInstance(SessionFactory.class);

    @Override
    public MovieSession add(MovieSession entity) {
        return null;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MovieSession> getAll() {
        return null;
    }
}
