package mate.academy.service;

import java.util.List;
import mate.academy.model.Movie;

public interface MovieService extends GenericService<Movie> {
    Movie add(Movie movie);

    Movie get(Long id);

    List<Movie> getAll();
}
