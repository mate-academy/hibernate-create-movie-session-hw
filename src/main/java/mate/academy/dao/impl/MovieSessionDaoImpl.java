package mate.academy.dao.impl;

import java.time.LocalDate; // Dodaj ten import
import java.time.LocalTime; // Dodaj ten import
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao; // Pamiętaj o adnotacji!
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id " + id, e);
        }
    }

    // Metoda findAvailableSessions MUSI być wewnątrz tej klamry
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getSessionsQuery = session.createQuery(
                    "FROM MovieSession ms " +
                            "WHERE ms.movie.id = :movieId " +
                            "AND ms.showTime BETWEEN :startTime AND :endTime", MovieSession.class);
            getSessionsQuery.setParameter("movieId", movieId);
            getSessionsQuery.setParameter("startTime", date.atStartOfDay());
            getSessionsQuery.setParameter("endTime", date.atTime(LocalTime.MAX));
            return getSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions for movie id: "
                    + movieId + " on date: " + date, e);
        }
    }
}
