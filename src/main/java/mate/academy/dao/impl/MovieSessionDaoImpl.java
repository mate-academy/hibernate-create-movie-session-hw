package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Couldn't create moviesession: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get a moviesession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime begTime = date.atStartOfDay();
        LocalDateTime endTime = begTime.plusDays(1);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailSessionsQuery = session.createQuery("FROM MovieSession"
                    + " WHERE movie.id = :movieId"
                    + " AND showTime >= :begTime AND showTime < :endTime"
                    + " ORDER BY showTime", MovieSession.class);
            getAvailSessionsQuery.setParameter("movieId", movieId);
            getAvailSessionsQuery.setParameter("begTime", begTime);
            getAvailSessionsQuery.setParameter("endTime", endTime);
            return getAvailSessionsQuery.getResultList();
        }
    }
}
