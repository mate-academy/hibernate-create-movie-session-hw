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
    private CinemaHallDao dao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return dao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return dao.get(id).orElseThrow();
    }

    @Override
    public List<CinemaHall> getAll() {
        return dao.getAll();
    }
}
