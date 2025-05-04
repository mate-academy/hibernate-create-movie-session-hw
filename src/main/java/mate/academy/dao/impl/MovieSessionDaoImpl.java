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
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endDay = LocalDateTime.of(date, LocalTime.MAX);
            Query<MovieSession> getMovieSessionByDate = session.createQuery("FROM MovieSession "
                    + "WHERE movie.id = :movieId "
                    + "AND showTime BETWEEN :startDay AND :endDay", MovieSession.class);
            getMovieSessionByDate.setParameter("movieId", movieId);
            getMovieSessionByDate.setParameter("startDay", startDay);
            getMovieSessionByDate.setParameter("endDay", endDay);
            return getMovieSessionByDate.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movieSessions", e);
        }
    }
}
