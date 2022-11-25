package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallService extends GeneralService<CinemaHall> {
    List<CinemaHall> getAll();
}
