package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    Optional<CinemaHall> get(Long id);

    Optional<List<CinemaHall>> getAll();
}
