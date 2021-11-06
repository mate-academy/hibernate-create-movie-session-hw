package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;
import java.time.LocalDate;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);

}
