package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.DaoOperation;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final DaoOperation<CinemaHall> daoOperation = new DaoOperation<>("cinema hall");

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return daoOperation.add(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return daoOperation.get(id, CinemaHall.class);
    }

    @Override
    public List<CinemaHall> getAll() {
        return daoOperation.getAll("from CinemaHall", CinemaHall.class);
    }
}
