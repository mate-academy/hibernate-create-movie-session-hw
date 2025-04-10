package mate.academy.service.impl;

import java.util.List;
import mate.academy.CinemaHall;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHalldao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHalldao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHalldao.get(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHalldao.getAll();
    }
}
