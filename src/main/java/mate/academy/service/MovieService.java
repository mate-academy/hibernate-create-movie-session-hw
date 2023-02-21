package mate.academy.service;

import java.util.List;
import mate.academy.lib.Dao;
import mate.academy.lib.Service;
import mate.academy.model.Movie;

@Dao
@Service
public interface MovieService {
    Movie add(Movie movie);

    Movie get(Long id);

    List<Movie> getAll();
}
