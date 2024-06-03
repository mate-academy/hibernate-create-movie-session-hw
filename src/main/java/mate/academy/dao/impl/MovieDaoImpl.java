package mate.academy.dao.impl;

import static mate.academy.util.HqlQueries.GET_ALL_MOVIES;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Dao
public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {

    public MovieDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Movie add(Movie movie) {
        return executeInsideTransaction(session -> {
            session.persist(movie);
            return movie;
        });
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Movie.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get a movie by id: " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (var session = factory.openSession()) {
            var movieList = session.createQuery(GET_ALL_MOVIES, Movie.class).list();
            return movieList != null ? movieList : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all movies", e);
        }
    }
}
