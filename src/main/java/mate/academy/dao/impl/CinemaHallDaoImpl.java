package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public class CinemaHallDaoImpl extends AbstractDao<CinemaHall> implements CinemaHallDao {
    public CinemaHallDaoImpl() {
        super(CinemaHall.class);
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        persistEntity(cinemaHall);
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return findEntity(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return findAllEntities();
    }
}
