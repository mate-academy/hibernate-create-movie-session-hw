package mate.academy.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.model.MovieSession;
import mate.academy.model.dto.MovieSessionDto;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    Optional<MovieSession> get(Long id);

    List<MovieSessionDto> findAvailableSession(Long movieId, LocalDate date);
}
