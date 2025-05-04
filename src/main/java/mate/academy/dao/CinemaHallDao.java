package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao {
    CinemaHall add(CinemaHall movie);

    Optional<CinemaHall> get(Long id);

    List<CinemaHall> getAll();
}
