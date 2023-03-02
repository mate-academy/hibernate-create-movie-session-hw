package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can not get MovieSession by id. id: " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        if (movieId == null || date == null) {
            throw new RuntimeException(
                    "For getting list of available MovieSessions movieId and date should be set. "
                            + "movieId: " + movieId + ", "
                            + "date: " + date);
        }
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
