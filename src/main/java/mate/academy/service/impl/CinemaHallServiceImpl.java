package mate.academy.service.impl;

import java.util.List;
import java.util.Optional;
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
        try {
            return cinemaHallDao.add(cinemaHall);
        } catch (Exception e) {
            throw new DataProcessingException("Can't add cinema hall " + cinemaHall, e);
        }
    }

    @Override
    public CinemaHall get(Long id) {
        Optional<CinemaHall> optional = cinemaHallDao.get(id);
        if (optional.isEmpty()) {
            throw new DataProcessingException("Cinema hall with id " + id + " not found", null);
        }
        return optional.get();
    }

    @Override
    public List<CinemaHall> getAll() {
        try {
            return cinemaHallDao.getAll();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}

