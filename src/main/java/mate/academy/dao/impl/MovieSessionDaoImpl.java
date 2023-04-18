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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add a movie session to database"
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAllMovieSessionsQuery
                    = session.createQuery("from MovieSession ms"
                    + " WHERE ms.movie.id = :movieId"
                    + " AND YEAR(ms.showTime) = :year"
                    + " AND MONTH(ms.showTime) = :month"
                    + " AND DAY(ms.showTime) = :day", MovieSession.class);
            findAllMovieSessionsQuery.setParameter("movieId", movieId);
            findAllMovieSessionsQuery.setParameter("year", date.getYear());
            findAllMovieSessionsQuery.setParameter("month", date.getMonthValue());
            findAllMovieSessionsQuery.setParameter("day", date.getDayOfMonth());
            return findAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get all movie sessions by id" + movieId + " for the day " + date, e);
        }
    }
}
