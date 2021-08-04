package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHalService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
