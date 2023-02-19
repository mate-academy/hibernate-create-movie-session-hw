package mate.academy.dao.impl;

import java.time.LocalDate;
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
        Session sessions = null;
        Transaction transaction = null;
        try {
            sessions = HibernateUtil.getSessionFactory().openSession();
            transaction = sessions.beginTransaction();
            sessions.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save movieSession to Db: " + movieSession, e);
        } finally {
            if (sessions != null) {
                sessions.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class,id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessionsQuery = session.createQuery(
                    "from MovieSession m "
                    + "WHERE m.showTime between :fromDate AND :toDate "
                    + "AND m.movie.id = :movieId", MovieSession.class);
            getAvailableSessionsQuery.setParameter("fromDate",date.atTime(LocalTime.MIN));
            getAvailableSessionsQuery.setParameter("toDate",date.atTime(LocalTime.MAX));
            getAvailableSessionsQuery.setParameter("movieId",movieId);
            return getAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSessions by movieId = " + movieId
            + "LocalDate = " + date, e);
        }
    }
}
