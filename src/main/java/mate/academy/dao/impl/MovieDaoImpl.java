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
import org.hibernate.query.Query;

@Dao
public class MovieDaoImpl implements MovieDao {

    private static final String CAN_T_GET_ALL_MOVIES_MSG = "Can't get all movies";
    private static final String CAN_T_GET_A_MOVIE_BY_ID_MSG = "Can't get a movie by id ";
    private static final String CAN_T_INSERT_MOVIE_MSG = "Can't insert movie ";

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
            throw new DataProcessingException(CAN_T_INSERT_MOVIE_MSG + movie, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Movie.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(CAN_T_GET_A_MOVIE_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> fromMovie =
                    session.createQuery("FROM Movie ", Movie.class);
            return fromMovie.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException(CAN_T_GET_ALL_MOVIES_MSG, e);
        }
    }
}
