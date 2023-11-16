package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Service;
import mate.academy.model.CinemaHall;

@Service
public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall);

    Optional<CinemaHall> get(Long id);

    List<CinemaHall> getAll();
}
