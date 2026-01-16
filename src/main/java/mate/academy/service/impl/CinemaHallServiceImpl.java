package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao chd;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return chd.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return chd.get(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return chd.getAll();
    }
}
