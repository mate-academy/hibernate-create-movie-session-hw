package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

import java.util.List;

@Dao
public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
