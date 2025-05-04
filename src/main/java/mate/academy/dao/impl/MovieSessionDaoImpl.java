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
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String QUERY_FOR_FIND_AVAILABLE_SESSION = "SELECT * FROM movie_sessions "
            + "WHERE movie_sessions.movie_id = ?1 AND DATE(movie_sessions.showTime) = ?2";

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory()
                    .openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            NativeQuery<MovieSession> nativeQuery = session
                    .createNativeQuery(QUERY_FOR_FIND_AVAILABLE_SESSION, MovieSession.class);
            nativeQuery.setParameter(1, movieId);
            nativeQuery.setParameter(2, date);
            return nativeQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find movie session with movie id: " + movieId
                    + " and date: " + date, e);
        }
    }
}
