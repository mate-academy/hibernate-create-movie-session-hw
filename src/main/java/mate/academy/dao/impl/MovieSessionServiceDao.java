package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;

public interface MovieSessionServiceDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
