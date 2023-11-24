package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallService {
    CinemaHall get(Long id);

    CinemaHall add(CinemaHall cinemaHall);

    List<CinemaHall> getAll();
}
