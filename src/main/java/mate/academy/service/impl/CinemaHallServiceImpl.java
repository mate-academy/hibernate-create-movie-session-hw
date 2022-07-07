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
    public CinemaHall add(CinemaHall entity) {
        return cinemaHallDao.add(entity);
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> cinemaHallOptional = cinemaHallDao.get(id);
        return cinemaHallOptional.orElseThrow(() ->
                new RuntimeException("Cinema hall with id " + id + " is not exist at DB"));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
