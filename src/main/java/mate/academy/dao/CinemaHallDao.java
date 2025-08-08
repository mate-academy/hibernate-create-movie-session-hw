package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao extends GenericDao<CinemaHall> {
    @Override
    CinemaHall create(CinemaHall entity);

    @Override
    List<CinemaHall> getAll();

    @Override
    Optional<CinemaHall> get(Long id);

    @Override
    void remove(CinemaHall entity);
}
