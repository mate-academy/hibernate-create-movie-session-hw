package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;

@Service
public class CinemaHallServiceImpl implements mate.academy.service.CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> optionalCinemaHall = cinemaHallDao.get(id);
        return optionalCinemaHall.orElseThrow(()
                -> new CinemaHallNotFoundException("CinemaHall not found with id: " + id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }

    // Доданий клас CinemaHallNotFoundException
    public static class CinemaHallNotFoundException extends RuntimeException {
        public CinemaHallNotFoundException(String message) {
            super(message);
        }
    }
}
