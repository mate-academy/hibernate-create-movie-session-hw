package mate.academy.dao;

import java.util.List;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;

@Dao
public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
