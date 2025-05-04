package mate.academy.dao;

import java.util.List;
import mate.academy.model.MovieSession;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    MovieSession get(Long id);

    List<MovieSession> getAll();
}
