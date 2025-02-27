package mate.academy.dao.impl;

import java.time.LocalDate;
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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Add movie session failed. MovieSession: "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession = session.createQuery("FROM MovieSession ms "
                    + "JOIN FETCH ms.movie "
                    + "JOIN FETCH ms.cinemaHall "
                    + "WHERE ms.id = :id", MovieSession.class);
            getMovieSession.setParameter("id", id);
            return getMovieSession.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    /** Method returns all sessions in a current date between 00:00 and 23:59:59
     * Implementation of a method may be different in accordance to requirements
     * Current impl returns all MovieSessions with Movie and CinemaHall
     * in a given date
     * @param movieId id of a given movie
     * @param date requested date
     * @return a List containing MovieSession elements
     */
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession = session.createQuery("FROM MovieSession ms "
                    + "JOIN FETCH ms.movie "
                    + "JOIN FETCH ms.cinemaHall "
                    + "WHERE ms.movie.id = :id "
                    + "AND DATE(ms.showTime) = :date", MovieSession.class);
            getMovieSession.setParameter("id", movieId);
            getMovieSession.setParameter("date", date);
            return getMovieSession.getResultStream().toList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions by movieId: "
                    + movieId + ", LocalDate: " + date, e);
        }
    }
}
