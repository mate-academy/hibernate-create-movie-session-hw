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
            throw new DataProcessingException("Can`t create MovieSession " + movieSession, e);
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
            throw new DataProcessingException("Can`t get MovieSession by id = " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSession =
                    session.createQuery("FROM MovieSession ms "
                            + "WHERE ms.movie.id = :id "
                            + "AND year(ms.showTime) = :year "
                            + "AND month(ms.showTime) = :month "
                            + "AND day(ms.showTime) = :day", MovieSession.class);
            getAllMovieSession.setParameter("id", movieId);
            getAllMovieSession.setParameter("year", date.getYear());
            getAllMovieSession.setParameter("month", date.getMonthValue());
            getAllMovieSession.setParameter("day", date.getDayOfMonth());
            return getAllMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get all MovieSessions with movie id = "
                    + movieId + " and date = " + date, e);
        }
    }
}
