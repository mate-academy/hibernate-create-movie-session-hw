package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionDao extends GenericDao<MovieSession, Long> {
    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
