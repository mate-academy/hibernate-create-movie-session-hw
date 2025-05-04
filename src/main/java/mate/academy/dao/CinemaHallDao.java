package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao extends GenericDao<CinemaHall> {
    @Override
    CinemaHall add(CinemaHall entity);

    @Override
    Optional<CinemaHall> get(Long id);

    @Override
    List<CinemaHall> getAll();
}
