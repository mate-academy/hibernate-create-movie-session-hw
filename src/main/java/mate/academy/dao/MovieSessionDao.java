package mate.academy.dao;

import mate.academy.model.MovieSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    Optional <List<MovieSession>> findAvailableSessions(Long movieId, LocalDate date);
}
