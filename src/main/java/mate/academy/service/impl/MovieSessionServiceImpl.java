package mate.academy.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Override
    public MovieSession add(MovieSession movieSession) {
        List<MovieSession> movieSessionsFromDb = movieSessionDao.findAll();
        boolean isMovieSessionExist = movieSessionsFromDb
                .stream()
                .anyMatch(ms ->
                        ms.getCinemaHall().getId().equals(movieSession.getCinemaHall().getId())
                                && isDateWithinRange(
                                        movieSession.getStartTime(),
                                ms.getStartTime(),
                                ms.getEndTime()));
        if (isMovieSessionExist) {
            throw new RuntimeException("Movie session already exists");
        }
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).orElseThrow(()
                -> new RuntimeException("Movie session not found"));
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return movieSessionDao.findAvailableSessions(movieId, date);
    }

    private boolean isDateWithinRange(LocalDateTime current,
                                      LocalDateTime start, LocalDateTime end) {
        return (current.isAfter(start) || current.equals(start))
                && (current.isBefore(end) || current.equals(end));
    }
}
