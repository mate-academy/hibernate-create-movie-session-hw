package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

@Dao
public class MovieDaoImpl extends AbstractDao implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        return super.add(movie);
    }

    @Override
    public Optional<Movie> get(Long id) {
        return super.get(Movie.class, id);
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Movie", Movie.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("The search for movies was failed", e);
        }
    }
}
