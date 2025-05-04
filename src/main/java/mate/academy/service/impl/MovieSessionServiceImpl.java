package mate.academy.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        return movieSessionDao.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionDao.get(id).get();
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> allMovieSessionDao = movieSessionDao.getAll();
        List<MovieSession> availableSessions = new ArrayList<>();

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        for (MovieSession movieSession : allMovieSessionDao) {
            if (Objects.equals(movieSession.getMovie().getId(), movieId)
                    && (!movieSession.getShowTime().isBefore(startOfDay)
                            && movieSession.getShowTime().isBefore(endOfDay))) {
                availableSessions.add(movieSession);
            }
        }
        return availableSessions;
    }
}
