package mate.academy.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
@AllArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {

    private final CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
