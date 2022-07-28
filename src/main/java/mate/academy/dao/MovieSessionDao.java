package mate.academy.dao;

import mate.academy.model.Movie;
import mate.academy.model.MovieSession;

import java.util.List;
import java.util.Optional;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> getAllAvailableMovieSessions();
}
