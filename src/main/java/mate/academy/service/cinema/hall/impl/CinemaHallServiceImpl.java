package mate.academy.service.cinema.hall.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.dao.cinema.hall.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.cinema.hall.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can't get cinema hall by id: " + id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
