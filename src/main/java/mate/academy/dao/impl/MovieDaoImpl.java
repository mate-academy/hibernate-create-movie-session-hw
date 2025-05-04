package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.DaoOperation;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;

@Dao
public class MovieDaoImpl implements MovieDao {
    private final DaoOperation<Movie> daoOperation = new DaoOperation<>("movie");

    @Override
    public Movie add(Movie movie) {
        return daoOperation.add(movie);
    }

    @Override
    public Optional<Movie> get(Long id) {
        return daoOperation.get(id, Movie.class);
    }

    @Override
    public List<Movie> getAll() {
        return daoOperation.getAll("from Movie", Movie.class);
    }
}
