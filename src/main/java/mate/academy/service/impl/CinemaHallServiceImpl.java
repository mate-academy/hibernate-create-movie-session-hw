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
    private final CinemaHallDao cinemaHallService;

    public CinemaHallServiceImpl(CinemaHallDao cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallService.add(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return cinemaHallService.get(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallService.getAll();
    }
}
