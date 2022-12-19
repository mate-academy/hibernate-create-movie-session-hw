package mate.academy.service;

import mate.academy.model.CinemaHall;

import java.util.List;

public interface CinemaHallService {
    CinemaHall add(CinemaHall movie);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
