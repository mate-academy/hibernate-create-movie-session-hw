package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallService {

    public CinemaHall add(CinemaHall cinemaHall);

    public CinemaHall get(Long id);

    public List<CinemaHall> getAll();
}
