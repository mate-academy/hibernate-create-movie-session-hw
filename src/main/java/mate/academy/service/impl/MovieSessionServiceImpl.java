package mate.academy.service.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.dao.impl.MovieSessionDaoImpl;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovieSessionServiceImpl implements MovieSessionService {
    private MovieSessionDao movieSessionDao = new MovieSessionDaoImpl();

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.create(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        Optional<MovieSession> movieSession = movieSessionDao.get(id);
        return movieSession.orElse(null);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
