package mate.academy.service.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

import java.time.LocalDateTime;
import java.util.List;

public class MovieSessionServiceImpl implements MovieSessionService {
    MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).get();
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
