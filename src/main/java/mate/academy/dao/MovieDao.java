package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.lib.Inject;
import mate.academy.model.Movie;

@Inject
public interface MovieDao {
    Movie add(Movie movie);

    Optional<Movie> get(Long id);

    List<Movie> getAll();
}
