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
    private CinemaHallDao cinemaHallService;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallService.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallService.get(id).get();
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallService.getAll();
    }
}
