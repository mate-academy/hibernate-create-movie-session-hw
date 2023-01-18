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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save a movie session to DB: "
                    + movieSession, e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryHql = "FROM MovieSession WHERE "
                    + "movie.id = :movie_Id"
                    +" AND showTime BETWEEN :start_Of_Day AND :end_Of_Day";
            Query<MovieSession> findAvailable = session.createQuery(queryHql,MovieSession.class);
            findAvailable.setParameter("movie_Id", movieId);
            findAvailable.setParameter("start_Of_Day", LocalTime.MIN.atDate(date));
            findAvailable.setParameter("end_Of_Day", LocalTime.MAX.atDate(date));
            return findAvailable.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available session by movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
