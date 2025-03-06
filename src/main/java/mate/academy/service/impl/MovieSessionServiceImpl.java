package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieSessionService;

public class MovieSessionServiceImpl implements MovieSessionService {

    private MovieSessionService movieSessionServiceService;

    @Override
    public MovieSession add(MovieSession movieSession) {
        return movieSessionServiceService.add(movieSession);
    }

    @Override
    public MovieSession get(Long id) {
        return movieSessionServiceService.get(id);
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        return movieSessionServiceService.findAvailableSessions(movieId, date);
    }
}
