package mate.academy.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
        return cinemaHallDao.get(id).orElseThrow(
                () -> new EntityNotFoundException("No cinema hall with id" + id + " found")
        );
    }

    @Override
    public List<CinemaHall> getAll() {
        List<CinemaHall> movies = cinemaHallDao.getAll();
        if (movies.isEmpty()) {
            throw new EntityNotFoundException("No cinema halls found in database");
        }
        return movies;
    }
}
