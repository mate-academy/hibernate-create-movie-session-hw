package mate.academy.service;

import mate.academy.model.MovieSession;
import org.hibernate.mapping.List;

import java.time.LocalDate;

public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
