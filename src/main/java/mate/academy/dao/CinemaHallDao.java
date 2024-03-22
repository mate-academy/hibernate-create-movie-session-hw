package mate.academy.dao;

import java.util.List;
import mate.academy.model.CinemaHall;

public interface CinemaHallDao extends GenericDao<CinemaHall, Long> {
    List<CinemaHall> getAll();
}
