package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;

public interface MovieSessionDao {
    MovieSession add(MovieSession movie);

    Optional<MovieSession> get(Long id);

    List<MovieSession> getMovieSessionsByDate(Long movieId, LocalDate date);
}
