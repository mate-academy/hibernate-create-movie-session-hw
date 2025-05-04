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
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't insert movie session: " + movieSession, exception);
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
            return Optional.ofNullable(session.get(MovieSession.class,id));
        } catch (Exception exception) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, exception);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableSessions = session.createQuery("FROM MovieSession WHERE "
                                    + "movie.id = :id "
                                    + "AND showTime BETWEEN :startDay AND :finishDay",
                            MovieSession.class)
                    .setParameter("id", movieId)
                    .setParameter("startDay", LocalTime.MIN.atDate(date))
                    .setParameter("finishDay", LocalTime.MAX.atDate(date));
            return availableSessions.list();
        } catch (Exception exception) {
            throw new DataProcessingException(
                    "Can`t find movie id:" + movieId + "at date :" + date.toString(), exception);
        }
    }
}
