package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.dao.impl.CinemaHallDaoImpl;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public CinemaHall get(Long id) {
        CinemaHall cinemaHall = new CinemaHall();
        try {
            cinemaHall = cinemaHallDao.get(id).get();
        } catch (Exception e) {
            throw new DataProcessingException("Cinema hall with id: "
                    + id + " dosen't exist in the database", e);
        }
        return cinemaHall;
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
