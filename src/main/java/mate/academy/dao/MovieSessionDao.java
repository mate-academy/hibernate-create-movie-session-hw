package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;

@Dao
public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
