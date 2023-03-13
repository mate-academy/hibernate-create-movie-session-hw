package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(
                () -> new NoSuchElementException("can't get movie's session by id: " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessionsLong(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
