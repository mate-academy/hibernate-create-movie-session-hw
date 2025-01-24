package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.lib.Inject;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private final CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id).get();
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
