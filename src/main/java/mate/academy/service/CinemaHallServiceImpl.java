package mate.academy.service;

import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.model.CinemaHall;

import java.util.List;

public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id).orElseThrow(
                () -> new RuntimeException("No such cinema hall with id: " + id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
