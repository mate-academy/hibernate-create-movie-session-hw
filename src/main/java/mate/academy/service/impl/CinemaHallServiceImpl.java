package mate.academy.service.impl;

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
        cinemaHallDao.add(cinemaHall);
        return cinemaHall;
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id)
                .orElseThrow(() -> new RuntimeException("cinemaHall not found"));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
