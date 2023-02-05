package mate.academy.dao;

import java.util.List;
import mate.academy.model.Movie;

public interface MovieDao extends GenericDao<Movie> {
    List<Movie> getAll();
}
