package mate.academy.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        return MovieDao.super.add(movie);
    }

    @Override
    public Optional<Movie> get(Long id) {
        return get(Movie.class, id);
    }

    @Override
    public List<Movie> getAll() {
        return getList("from Movie", Collections.emptyMap(), Movie.class);
    }
}
