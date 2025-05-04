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
    @Inject
    private MovieSessionDao dao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return dao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return dao.get(id)
                .orElseThrow(() -> new RuntimeException("Can't get movie session by id: " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long moveId, LocalDate date) {
        return dao.findAvailableSessions(moveId, date);
    }
}
