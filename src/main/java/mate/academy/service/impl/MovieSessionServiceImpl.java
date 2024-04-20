package mate.academy.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.Main;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao =
            (MovieSessionDao) Main.injector.getInstance(MovieSessionDao.class);

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(
                () -> new EntityNotFoundException("Not found in DB movieSession with id = " + id));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime fromDateTime = date.atStartOfDay();
        LocalDateTime toDateTime = date.plusDays(1).atStartOfDay();
        return movieSessionDao.findAvailableSessions(movieId, fromDateTime, toDateTime);
    }
}
