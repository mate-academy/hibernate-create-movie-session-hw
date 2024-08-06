package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;
import mate.academy.service.CinemaHallService;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        if (cinemaHall != null) {
            return cinemaHallDao.add(cinemaHall);
        }
        throw new DataProcessingException("The argument (cinemaHall) is null.");
    }

    @Override
    public CinemaHall get(Long id) {
        if (id != null) {
            return cinemaHallDao.get(id).orElseThrow(()
                    -> new DataProcessingException("Can't get cinema Hall by id, result is null. "
                    + id));
        }
        throw new DataProcessingException("The argument (cinemaHall) is null.");
    }

    @Override
    public List<CinemaHall> getAll() {
        List<CinemaHall> cinemaHalls = cinemaHallDao.getAll();
        if (!cinemaHalls.isEmpty()) {
            return cinemaHalls;
        }
        throw new DataProcessingException("Can't get list of all cinema Halls, list is empty.");
    }
}
