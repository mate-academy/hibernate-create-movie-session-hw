package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaDao {

    CinemaHall add(CinemaHall movie);

    CinemaHall get(Long id);

    List<CinemaHall> getAll();
}
