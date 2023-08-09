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
            throw new DataProcessingException("Can't add movie session: " + movieSession, e);
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
            throw new DataProcessingException("Can't get by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session currentSession = HibernateUtil.getSessionFactory().openSession();) {
            Query<MovieSession> findAvailableSessions = currentSession.createQuery(
                         "from MovieSession"
                                    + " where id = :value and DAY(localDateTime) = :dateValue ",
                            MovieSession.class);
            findAvailableSessions.setParameter("value", movieId);
            findAvailableSessions.setParameter("dateValue", date.getDayOfMonth());
            return findAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions", e);
        }
    }
}
