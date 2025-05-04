package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add movieSession "
                    + movieSession + " to DB", e);
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
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get movieSession by id = "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableMovieSessions = session.createQuery("From MovieSession "
                            + "Where movie_id = :movieId "
                            + "AND showTime BETWEEN :beginOfDay AND :endOfDay",
                    MovieSession.class);
            availableMovieSessions.setParameter("movieId", movieId);
            availableMovieSessions.setParameter("beginOfDay", LocalTime.MIN.atDate(date));
            availableMovieSessions.setParameter("endOfDay", LocalTime.MAX.atDate(date));
            return availableMovieSessions.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't find any available movieSessions by movie ID = "
                    + movieId + " and showTime = " + date, e);
        }
    }
}
