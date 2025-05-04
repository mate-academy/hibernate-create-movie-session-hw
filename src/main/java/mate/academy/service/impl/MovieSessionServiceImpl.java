package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(final MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(final Long id) {
        return movieSessionDao.get(id)
                .orElseThrow(() -> new RuntimeException("Can't get a movieSession by id: " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessions(final Long movieId, final LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }
}
