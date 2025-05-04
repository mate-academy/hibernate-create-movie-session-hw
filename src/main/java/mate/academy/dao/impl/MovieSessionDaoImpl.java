package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession: " + movieSession, e);
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
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Query<MovieSession> getAvailableSessions = session.createNamedQuery(
                    "from MovieSession "
                    + "where movieId = :movieId "
                    + "and showTime between :startTime and :endTime", MovieSession.class);
            getAvailableSessions.setParameter("movieId", movieId);
            getAvailableSessions.setParameter("startTime", startTime);
            getAvailableSessions.setParameter("endTime", endTime);
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't find available movieSession by id: " + movieId, e);
        }
    }
}
