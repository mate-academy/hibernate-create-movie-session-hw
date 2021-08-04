package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

import java.util.List;
import java.util.Optional;

@Dao
public interface CinemaHallDao {
    CinemaHall add(CinemaHall CinemaHall);

    Optional<CinemaHall> get(Long id);

    List<CinemaHall> getAll();
}
