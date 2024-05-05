package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public mate.academy.model.CinemaHall add(mate.academy.model.CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public mate.academy.model.CinemaHall get(Long id) {
        return cinemaHallDao.get(id).orElseThrow(() ->
                new DataProcessingException("cannot find cinema hall with id: " + id));
    }

    @Override
    public List<mate.academy.model.CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
