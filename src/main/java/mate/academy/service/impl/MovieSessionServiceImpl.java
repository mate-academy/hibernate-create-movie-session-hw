package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;
    @Inject
    private MovieDao movieDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        Optional<MovieSession> movieSession = movieSessionDao.get(id);
        return movieSession.orElseThrow(() ->
                new RuntimeException("Session with id " + id + " not found."));

    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> movieSessions = movieSessionDao.findAvailableSessions(movieId, date);
        if (movieSessions.isEmpty()) {
            throw new RuntimeException("No sessions available for "
                    + movieDao.get(movieId).get() + " on " + date + ".");
        }
        return movieSessions;
    }

    @Override
    public List<MovieSession> findAll() {
        List<MovieSession> movieSessions = movieSessionDao.findAll();
        if (movieSessions.isEmpty()) {
            throw new RuntimeException("No sessions found.");
        }
        return movieSessions;
    }
}
