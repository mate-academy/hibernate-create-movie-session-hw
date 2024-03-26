package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dto.MovieSessionsByDate;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;

@Dao
public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSessionsByDate> findAvailableSessions(Long movieId, LocalDate date);
}
