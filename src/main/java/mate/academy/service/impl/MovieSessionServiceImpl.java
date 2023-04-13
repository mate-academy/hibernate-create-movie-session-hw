package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Injector;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private final MovieSessionDao movieSessionDao =
            (MovieSessionDao) injector.getInstance(MovieSessionDao.class);

    @Override
    public MovieSession add(MovieSession entity) {
        return null;
    }

    @Override
    public MovieSession get(Long id) {
        return null;
    }

    @Override
    public List<MovieSession> getAll() {
        return null;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return null;
    }
}
