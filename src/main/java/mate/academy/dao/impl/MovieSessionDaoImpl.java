package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
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
            throw new DataProcessingException("Can`t insert movieSession: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get MovieSession by id: " + id, e);
        }
    }

    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(
                    "FROM MovieSession movieSession "
                            + "WHERE movieSession.movie.id = :movieId "
                            + "AND movieSession.showTime BETWEEN :start AND :end",
                    MovieSession.class
            );
            query.setParameter("movieId", movieId);
            query.setParameter("start", date.atStartOfDay());
            query.setParameter("end", date.atTime(java.time.LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find "
                    + "available session for " + movieId + " on date " + date, e);
        }
    }
}
