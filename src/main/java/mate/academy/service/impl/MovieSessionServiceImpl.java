package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

@Service
public class MovieSessionServiceImpl implements mate.academy.service.MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        if (movieSession != null) {
            return movieSessionDao.add(movieSession);
        }
        throw new RuntimeException("Movie session can't be null!");
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(
                () -> new RuntimeException("Can't find a movie session with id: " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        if (movieId != null && date != null) {
            return movieSessionDao.findAvailableSessions(movieId, date);
        }
        throw new RuntimeException("Input fields movie Id and date can't be null!");
    }
}
