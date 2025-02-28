package mate.academy.service.impl;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallServicel {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
