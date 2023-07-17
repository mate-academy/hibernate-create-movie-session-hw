package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionService extends GenericService<MovieSession> {
    @Override
    MovieSession add(MovieSession entity);

    @Override
    MovieSession get(Long id);

    @Override
    List<MovieSession> getAll();

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
