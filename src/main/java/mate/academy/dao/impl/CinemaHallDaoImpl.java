package mate.academy.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return CinemaHallDao.super.add(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return get(CinemaHall.class, id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return getList("from CinemaHall", Collections.emptyMap(), CinemaHall.class);
    }
}
