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
            throw new DataProcessingException("Can't add movie session to DB " + movieSession, e);
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
            throw new DataProcessingException("Can't get movie session from DB by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionQueries
                    = session.createQuery("from MovieSession "
                    + "where movie.id = :movieId "
                    + "and showTime between "
                    + ":startDateTime and :endDateTime", MovieSession.class);
            getAllAvailableSessionQueries.setParameter("movieId", movieId);
            getAllAvailableSessionQueries.setParameter("startDateTime", date.atStartOfDay());
            getAllAvailableSessionQueries.setParameter("endDateTime", date.atTime(LocalTime.MAX));
            return getAllAvailableSessionQueries.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get available sessions from DB by id "
                            + movieId + " and date: " + date, e);
        }
    }
}
