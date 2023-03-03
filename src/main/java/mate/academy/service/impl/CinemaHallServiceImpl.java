package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaDao cinemaDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        return cinemaDao.get(id).orElseThrow(()
                -> new DataProcessingException("Can't get CinemaHall by id: " + id));
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaDao.getAll();
    }
}
