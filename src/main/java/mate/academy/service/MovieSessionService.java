package mate.academy.service;

import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);

}
