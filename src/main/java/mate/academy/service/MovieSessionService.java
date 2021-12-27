package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionService {
    public MovieSession add(MovieSession movieSession);

    public MovieSession get(Long id);

    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
