package mate.academy.service;

import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;

import java.util.List;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
