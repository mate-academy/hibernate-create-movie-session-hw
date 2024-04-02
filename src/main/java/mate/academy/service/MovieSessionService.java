package mate.academy.service;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

@Service
public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date);
}
