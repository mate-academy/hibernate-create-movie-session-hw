package mate.academy.service.impl;

import java.time.LocalDate;
import mate.academy.model.MovieSession;
import java.util.List;

public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
