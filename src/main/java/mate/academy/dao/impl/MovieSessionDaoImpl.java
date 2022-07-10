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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
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
            MovieSession movieSession = session.get(MovieSession.class, id);
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startTime = date.atTime(LocalTime.MIN);
        LocalDateTime endTime = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionQuery
                    = session.createQuery("from MovieSession ms "
                    + "where ms.id = :movieId and ms.showTime between :startTime "
                    + "and :endTime", MovieSession.class);
            getAllMovieSessionQuery.setParameter("movieId", movieId);
            getAllMovieSessionQuery.setParameter("startTime", startTime);
            getAllMovieSessionQuery.setParameter("endTime", endTime);
            return getAllMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available MovieSession", e);
        }
    }
}
