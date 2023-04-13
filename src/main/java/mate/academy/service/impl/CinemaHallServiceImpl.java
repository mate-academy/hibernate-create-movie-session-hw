package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Injector;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private final CinemaHallDao cinemaHallDao =
            (CinemaHallDao) injector.getInstance(CinemaHallDao.class);

    @Override
    public CinemaHall add(CinemaHall entity) {
        return null;
    }

    @Override
    public CinemaHall get(Long id) {
        return null;
    }

    @Override
    public List<CinemaHall> getAll() {
        return null;
    }
}
