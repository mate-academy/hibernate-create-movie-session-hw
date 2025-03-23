package mate.academy.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.model.CinemaHall;
import mate.academy.model.MovieSession;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);
}
