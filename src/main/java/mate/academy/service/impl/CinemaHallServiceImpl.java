package mate.academy.service.impl;

import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

import java.util.List;
import java.util.Optional;

public class CinemaHallServiceImpl implements CinemaHallService {
    private CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.create(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> cinemaHall = cinemaHallDao.get(id);
        return cinemaHall.orElse(null);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
