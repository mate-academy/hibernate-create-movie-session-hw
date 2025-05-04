package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;

public interface MovieSessionDao extends GenericDao<MovieSession> {
    @Override
    MovieSession add(MovieSession entity);

    @Override
    Optional<MovieSession> get(Long id);

    @Override
    List<MovieSession> getAll();

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
