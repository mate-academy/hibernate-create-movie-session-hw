package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.impl.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

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
        Optional<MovieSession> movieSession = movieSessionDao.get(id);
        return movieSession.orElseThrow(() -> new RuntimeException(
                "Cannot find a movie sessio with id: " + id));
    }

    @Override
    public List<MovieSession> findAvaibleSessions(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
