package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
            Query<MovieSession> getMovieSession = session.createQuery("FROM MovieSession m "
                            + "WHERE movie.id = :id AND EXTRACT(DAY FROM localDateTime) = :day "
                            + "AND EXTRACT(MONTH FROM localDateTime) = :month " +
                            "AND EXTRACT(YEAR FROM localDateTime) = :year", MovieSession.class);
            getMovieSession.setParameter("id", movieId);
            getMovieSession.setParameter("day", date.getDayOfMonth());
            getMovieSession.setParameter("month", date.getMonthValue());
            getMovieSession.setParameter("year", date.getYear());
            return getMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get all movieSessions by id" + movieId, e);
        }
    }
}
