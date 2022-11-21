package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.GenericDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl extends GenericServiceImpl<MovieSession>
        implements MovieSessionService {
    @Inject
    private MovieSessionDao dao;

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return dao.findAvailableSessions(movieId, date);
    }

    @Override
    protected GenericDao<MovieSession> getDao() {
        return dao;
    }
}
