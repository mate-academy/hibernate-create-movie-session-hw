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
            Query<MovieSession> getBookingQuery =
                    session.createQuery("SELECT ms FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.movie AS m "
                    + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                    + "WHERE m.id = :id", MovieSession.class);
            return getBookingQuery.setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "SELECT ms FROM MovieSession ms "
                + "WHERE ms.movie.id =:movieId "
                + "AND ms.showTime between :start and :end";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByIdAndDate =
                    session.createQuery(query, MovieSession.class);
            LocalDateTime start = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
            getMovieSessionByIdAndDate.setParameter("movieId", movieId);
            getMovieSessionByIdAndDate.setParameter("start", start);
            getMovieSessionByIdAndDate.setParameter("end", end);
            return getMovieSessionByIdAndDate.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by date: " + date, e);
        }
    }
}
