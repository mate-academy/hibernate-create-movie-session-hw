package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dto.MovieSessionsByDate;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;

@Service
public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSessionsByDate> findAvailableSessions(Long movieId, LocalDate date);
}
