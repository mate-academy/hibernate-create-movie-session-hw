package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

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
        Optional<CinemaHall> cinemaHall = cinemaHallDao.get(id);
        if (cinemaHall.isEmpty()) {
            throw new RuntimeException("Can't found cinema hall with id " + id);
        }
        return cinemaHall.get();
    }

    @Override
    public List<CinemaHall> getAll() {
        List<CinemaHall> cinemaHalls = cinemaHallDao.getAll();
        if (cinemaHalls.isEmpty()) {
            throw new RuntimeException("Can't found all cinema halls.");
        }
        return cinemaHalls;
    }
}
