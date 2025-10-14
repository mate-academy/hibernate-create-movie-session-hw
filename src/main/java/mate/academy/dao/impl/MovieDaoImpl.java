package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't insert movie " + movie, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            Movie movie = session.find(Movie.class, id);
            if (movie != null) {
                return Optional.of(movie);
            }
        } catch (RuntimeException e) {
            throw new DataProcessingException(
                    "Can't get movie with id " + id + " from database", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            return session.createQuery("from Movie ", Movie.class).list();
        }
    }
}
