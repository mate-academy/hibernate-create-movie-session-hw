package mate.academy.service.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.util.List;

public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;
    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(
                () -> new RuntimeException("No such session with id: " + id));
    }

    @Override
    public List<MovieSession> getAllByMovieIdAndDate(Long movieId, LocalDate localDate) {
        return movieSessionDao.getAllByMovieIdAndDate(movieId, localDate);
    }
}
