package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

@Service
public class MovieSessionServiceImpl implements MovieSessionDao {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return Optional.of(movieSessionDao.get(id).orElseThrow(
                () -> new DataProcessingException("Cant get movieSession by id: " + id)));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId,date);
    }
}
