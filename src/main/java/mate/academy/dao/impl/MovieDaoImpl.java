package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;

@Dao
public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {

    public MovieDaoImpl() {
        super(Movie.class);
    }

    @Override
    public Movie add(Movie movie) {
        persistEntity(movie);
        return movie;
    }

    @Override
    public Optional<Movie> get(Long id) {
        return findEntity(id);
    }

    @Override
    public List<Movie> getAll() {
        return findAllEntities();
    }
}
