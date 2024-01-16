package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    public MovieSessionServiceImpl(MovieSessionDao movieSessionDao) {
        this.movieSessionDao = movieSessionDao;
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return Optional.ofNullable(movieSessionDao.get(id))
                .orElseThrow(() ->
                        new DataProcessingException("Can't find movieSession by id = " + id));
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSession(movieId, date);
    }
}
