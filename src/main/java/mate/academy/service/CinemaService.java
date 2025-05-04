package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
