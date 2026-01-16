package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao msd;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return msd.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return msd.get(id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return msd.findAvailableSessions(movieId, date);
    }
}
