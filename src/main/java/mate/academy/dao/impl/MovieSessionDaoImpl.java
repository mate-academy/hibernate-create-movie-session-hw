package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String SELECT_AVAILABLE_MOVIE_SESSIONS_QUERY =
            "from MovieSession ms where ms.id = :movieId "
                    + "AND DATE(ms.showTime) = :date ";
    private static final String CANT_GET_MOVIE_SESSION_EXCEPTION_MESSAGE =
            "Can't get a MovieSession by id: ";
    private static final String CANT_ADD_MOVIE_SESSION_EXCEPTION_MESSAGE =
            "Can't add MovieSession ";
    private static final String CANT_GET_ALL_MOVIE_SESSIONS_EXCEPTION_MESSAGE =
            "Can't get all available MovieSessions";
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CANT_ADD_MOVIE_SESSION_EXCEPTION_MESSAGE
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(CANT_GET_MOVIE_SESSION_EXCEPTION_MESSAGE + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery = session.createQuery(
                    SELECT_AVAILABLE_MOVIE_SESSIONS_QUERY, MovieSession.class);
            getAllMovieSessionsQuery.setParameter("movieId", movieId);
            getAllMovieSessionsQuery.setParameter("date", date);
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(CANT_GET_ALL_MOVIE_SESSIONS_EXCEPTION_MESSAGE, e);
        }
    }
}
