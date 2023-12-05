package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Movie;

public interface MovieDao {
    Movie add(Movie movie);

    Optional<Movie> get(Long id);

    List<Movie> getAll();
}
