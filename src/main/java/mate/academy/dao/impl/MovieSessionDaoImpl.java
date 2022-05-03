package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime finishDate = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessions = session.createQuery(
                    "from MovieSession ms where ms.movie.id = :value "
                    + "and ms.showTime between :startTime and :finishTime", MovieSession.class);
            getAllAvailableSessions.setParameter("value", movieId);
            getAllAvailableSessions.setParameter("startTime", startDate);
            getAllAvailableSessions.setParameter("finishTime", finishDate);
            return getAllAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available session by movie id: " + movieId
                    + " and date: " + date, e);
        }
    }
}
