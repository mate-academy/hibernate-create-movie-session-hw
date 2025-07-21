package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import org.hibernate.SessionFactory;

@Dao
public class MovieDaoImpl extends AbstractDao implements MovieDao {
    protected MovieDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public MovieDaoImpl() {
    }

    @Override
    public Movie add(Movie movie) {
        return persistEntity(movie);
    }

    @Override
    public Optional<Movie> get(Long id) {
        return Optional.ofNullable(getEntity(Movie.class, id));
    }

    @Override
    public List<Movie> getAll() {
        return getAllEntity(Movie.class);
    }
}
