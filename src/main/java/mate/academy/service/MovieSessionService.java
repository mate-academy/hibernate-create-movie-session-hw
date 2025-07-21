package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;
import mate.academy.model.dto.MovieSessionDto;

public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSessionDto> findAvailableSession(Long movieId, LocalDate date);
}
