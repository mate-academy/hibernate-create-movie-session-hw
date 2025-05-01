package mate.academy.dao;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionDao {

    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId,
                                             LocalDateTime from,
                                             LocalDateTime to);
}
