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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        // Start point from 00.00
        LocalDateTime start = date.atStartOfDay();
        // End point to 23:59:59.999999999
        LocalDateTime end = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllSessionsBetweenStartAndEnd = session.createQuery(
                    "FROM MovieSession m "
                            + "LEFT JOIN m.movie "
                    + "WHERE m.movie.id = :id AND m.showTime BETWEEN :start AND :end",
                    MovieSession.class
            );
            getAllSessionsBetweenStartAndEnd.setParameter("id", movieId);
            getAllSessionsBetweenStartAndEnd.setParameter("start", start);
            getAllSessionsBetweenStartAndEnd.setParameter("end", end);
            return getAllSessionsBetweenStartAndEnd.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get a movie session at " + date + "with movie " + movieId, e);
        }
    }
}
