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
            throw new DataProcessingException(
                    "Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MovieSession movieSession = session.get(MovieSession.class, id);
            Query<MovieSession> getMovieSessionById = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "WHERE ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return getMovieSessionById.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get the movie session by id: " + id, e);
        }
    }

    @Override
    public Optional<List<MovieSession>> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);
            Query<MovieSession> query = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "WHERE ms.movie.id = :movieId AND ms.showTime "
                            + "BETWEEN :startTime AND :endTime",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("startTime", startOfDay);
            query.setParameter("endTime", endOfDay);
            List<MovieSession> availableSessions = query.getResultList();
            return Optional.ofNullable(availableSessions);
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't find available session by movie id: " + movieId
                            + " and date: " + date, e);
        }
    }
}
