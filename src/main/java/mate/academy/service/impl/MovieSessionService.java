package mate.academy.service.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionService {

    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvaibleSessions(Long movieId, LocalDate date);
}
