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
            session = openSession();
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            transactionNullCheck(transaction);
            throw new DataProcessingException("Could not insert movie " + movie, e);
        } finally {
            sessionNullCheck(session);
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = openSession()) {
            return Optional.ofNullable(session.get(Movie.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Could not get a movie by id: " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = openSession()) {
            List<Movie> movieList = session.createQuery("from Movie", Movie.class).getResultList();
            return movieList;
        } catch (Exception e) {
            throw new DataProcessingException("Could not get movie list from DB", e);
        }
    }

    private Session openSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    private void transactionNullCheck(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private void sessionNullCheck(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
