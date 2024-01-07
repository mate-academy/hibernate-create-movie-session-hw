package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Inject;
import mate.academy.model.CinemaHall;

@Inject
public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    Optional<CinemaHall> get(Long id);

    List<CinemaHall> getAll();
}
