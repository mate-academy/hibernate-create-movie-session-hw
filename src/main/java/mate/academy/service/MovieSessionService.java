package mate.academy.service;

import mate.academy.model.*;

import java.time.*;
import java.util.*;

public interface MovieSessionService {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
