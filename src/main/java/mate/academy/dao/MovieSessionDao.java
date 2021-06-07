package mate.academy.dao;

import java.util.List;
import mate.academy.model.MovieSession;
import java.time.LocalDate;
import java.util.Optional;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
