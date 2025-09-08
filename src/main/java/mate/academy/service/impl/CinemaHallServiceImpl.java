package mate.academy.service.impl;

import java.util.List;
import mate.academy.lib.Inject;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaDao;

public class CinemaHallServiceImpl implements CinemaDao {
    @Inject
    private final CinemaDao cinemaDao;

    public CinemaHallServiceImpl(CinemaDao cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaDao.get(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaDao.getAll();
    }
}
