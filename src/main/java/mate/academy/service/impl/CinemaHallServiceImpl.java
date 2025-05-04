package mate.academy.service.impl;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    public CinemaHall get(Long id) {
        return cinemaHallDao.get(id).orElseThrow(() ->
                new EntityNotFoundException("Not found cinema hall by id: " + id));
    }

    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
