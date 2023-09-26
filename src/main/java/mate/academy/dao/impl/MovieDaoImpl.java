package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import mate.academy.util.HibernateUtil;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        try {
            HibernateUtil.getFactory().inTransaction(session -> session.persist(movie));
            return movie;
        } catch (Exception e) {
            throw new DataProcessingException("Can't insert movie " + movie, e);
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try {
            return HibernateUtil.getFactory()
                    .fromSession(session -> Optional.ofNullable(session.get(Movie.class, id)));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie by id: " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try {
            return HibernateUtil.getFactory()
                    .fromSession(session -> session.createQuery("FROM Movie", Movie.class)
                    .getResultList());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of movies", e);
        }
    }
}
