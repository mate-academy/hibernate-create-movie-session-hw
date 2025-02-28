package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.impl.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;

@Service
public class CinemaHallServiceImpl implements CinemaHallServicel {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> cinemaHall = cinemaHallDao.get(id);
        return cinemaHall.orElseThrow(() -> new RuntimeException("Cannot find a "
                + "cinema hall with id: "
                + id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
