package mate.academy.service;

import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;

import java.util.List;

@Service
public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
