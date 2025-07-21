package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import org.hibernate.SessionFactory;

@Dao
public class CinemaHallDaoImpl extends AbstractDao implements CinemaHallDao {
    protected CinemaHallDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public CinemaHallDaoImpl() {
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return persistEntity(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return Optional.ofNullable(getEntity(CinemaHall.class, id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return getAllEntity(CinemaHall.class);
    }
}
