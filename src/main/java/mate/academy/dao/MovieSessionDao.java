package mate.academy.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSession> findMovieSessionsByMovieIdAndTime(Long movieId, LocalDateTime timeFrom,
                                                         LocalDateTime timeTo);
}
