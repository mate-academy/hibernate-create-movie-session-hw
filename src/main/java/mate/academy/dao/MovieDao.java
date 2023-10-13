package mate.academy.dao;

import java.util.List;
import mate.academy.model.Movie;

public interface MovieDao {
    Movie add(Movie movie);

    Movie get(Long id);

    List<Movie> getAll();
}
