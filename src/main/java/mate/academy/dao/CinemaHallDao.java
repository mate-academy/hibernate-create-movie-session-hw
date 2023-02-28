package mate.academy.dao;

import java.util.List;
import java.time.LocalDate;
import mate.academy.model.MovieSession;

public interface CinemaHallDao {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
