package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import org.hibernate.SessionFactory;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private final SessionFactory sessionFactory =
            (SessionFactory) injector.getInstance(SessionFactory.class);

    @Override
    public CinemaHall add(CinemaHall entity) {
        return null;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CinemaHall> getAll() {
        return null;
    }
}
