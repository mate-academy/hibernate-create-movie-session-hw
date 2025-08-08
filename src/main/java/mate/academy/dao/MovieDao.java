package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Movie;

public interface MovieDao extends GenericDao<Movie> {

    Movie add(Movie movie);

    @Override
    Movie create(Movie entity);

    @Override
    Optional<Movie> get(Long id);

    @Override
    List<Movie> getAll();

    @Override
    void remove(Movie entity);
}
