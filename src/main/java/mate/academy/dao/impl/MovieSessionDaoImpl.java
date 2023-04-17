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
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add a movieSession " + movieSession, e);
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
            Query<MovieSession> findAllMovieSessionsQuery =
                    session.createQuery("from MovieSession ms WHERE ms.id = :movieId"
                    + " AND YEAR(ms.showTime) = :year"
                    + " AND MONTH(ms.showTime) = :month"
                    + " AND DAY(ms.showTime) = :day", MovieSession.class);
            findAllMovieSessionsQuery.setParameter("movieId", movieId);
            findAllMovieSessionsQuery.setParameter("year", date.getYear());
            findAllMovieSessionsQuery.setParameter("month", date.getMonthValue());
            findAllMovieSessionsQuery.setParameter("day", date.getDayOfMonth());
            return findAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get from DB all movieSessions by id "
                    + movieId + "for the day " + date, e);
        }
    }
}
