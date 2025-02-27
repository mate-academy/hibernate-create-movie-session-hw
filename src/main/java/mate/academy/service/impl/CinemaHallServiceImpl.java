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
        Optional<CinemaHall> cinemaHall = dao.get(id);
        if (cinemaHall.isPresent()) {
            return cinemaHall.get();
        }
        throw new RuntimeException("CinemaHall with id " + id + " not is not present");
    }

    @Override
    public List<CinemaHall> getAll() {
        return dao.getAll();
    }
}
