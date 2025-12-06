package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao {
    public CinemaHall add(CinemaHall cinemaHall);

    public Optional<CinemaHall> get(Long id);

    public List<CinemaHall> getAll();
}
