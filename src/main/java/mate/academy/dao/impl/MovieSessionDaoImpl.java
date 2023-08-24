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
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsForDateQuery =
                    session.createQuery("from MovieSession ms "
                                    + "JOIN FETCH ms.movie "
                                    + "JOIN FETCH ms.cinemaHall "
                                    + "WHERE ms.movie.id = :movieId "
                                    + "AND ms.showTime BETWEEN :startOfDay AND :endOfDay",
                            MovieSession.class);
            getAllMovieSessionsForDateQuery.setParameter("movieId", movieId);
            getAllMovieSessionsForDateQuery.setParameter("startOfDay", date.atStartOfDay());
            getAllMovieSessionsForDateQuery.setParameter("endOfDay", date.atTime(LocalTime.MAX));
            return getAllMovieSessionsForDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get list of movieSessions for date: " + date, e);
        }
    }
}
