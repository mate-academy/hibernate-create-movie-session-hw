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
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = HibernateUtil.getSessionFactory().openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session: "
                    + movieSession + " to DB", e);
        } finally {
            currentSession.close();
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session currentSession = HibernateUtil.getSessionFactory().openSession();) {
            return Optional.ofNullable(currentSession.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session currentSession = HibernateUtil.getSessionFactory().openSession();) {
            Query<MovieSession> findAvailableSessionsQuery = currentSession.createQuery(
                    "from MovieSession sessions"
                            + " where movie.id = :movieId"
                            + " and DATE(sessions.showtime) = :dateValue", MovieSession.class);
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            findAvailableSessionsQuery.setParameter("dateValue", date);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions from DB", e);
        }
    }
}
