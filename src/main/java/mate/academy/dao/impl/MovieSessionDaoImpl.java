package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinema hall: " + movieSession, ex);
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
            return session.get(MovieSession.class, id);
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get cinema hall with id: "
                    + id, ex);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfTheDay = LocalTime.MIN.atDate(date);
        LocalDateTime endOfTheDay = LocalTime.MAX.atDate(date);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> queryOfFilms = session.createQuery("from MovieSession m "
                    + "where m.id = :movieId "
                    + "and m.showTime between :startOfTheDay and :endOfTheDay", MovieSession.class);
            queryOfFilms.setParameter("movieId", movieId);
            queryOfFilms.setParameter("startOfTheDay", startOfTheDay);
            queryOfFilms.setParameter("endOfTheDay", endOfTheDay);
            return queryOfFilms.getResultList();
        } catch (Exception ex) {
            throw new DataProcessingException("No movies with id: "
                    + movieId + "on: "
                    + date, ex);
        }
    }
}
