package mate.academy.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

public class CinemaHallServiceImpl implements CinemaHallService {
    private final CinemaHallDao cinemaHallDao;

    public CinemaHallServiceImpl(CinemaHallDao cinemaHallDao) {
        this.cinemaHallDao = cinemaHallDao;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cinema hall with id " + id + " not found",
                        new NoSuchElementException("No such element found with id " + id)));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
