package mate.academy.service;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallService extends GenericService<CinemaHall> {
    @Override
    CinemaHall add(CinemaHall entity);

    @Override
    CinemaHall get(Long id);

    @Override
    List<CinemaHall> getAll();
}
