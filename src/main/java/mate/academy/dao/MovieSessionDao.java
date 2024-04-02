package mate.academy.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;

@Dao
public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date);
}
