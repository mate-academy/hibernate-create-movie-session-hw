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
        CinemaHall newCinemaHall = cinemaHallDao.add(cinemaHall);
        return newCinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        Optional<CinemaHall> cinemaHallFromDb = cinemaHallDao.get(id);
        return cinemaHallFromDb;
    }

    @Override
    public List<CinemaHall> getAll() {
        List<CinemaHall> allCinemaHallsFromDb = cinemaHallDao.getAll();
        return allCinemaHallsFromDb;
    }
}
