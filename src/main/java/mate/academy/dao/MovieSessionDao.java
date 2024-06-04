package mate.academy.dao;

import mate.academy.model.MovieSession;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao extends DataAccess<MovieSession> {
    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
