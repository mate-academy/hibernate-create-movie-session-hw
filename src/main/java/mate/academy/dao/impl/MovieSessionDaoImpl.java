package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add MovieSession to DB "
                                                                        + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get MovieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hqlQuery = "FROM MovieSession WHERE movie.id = :movieId "
                    + "AND showTime >= :startTime AND showTime <= :endTime";
            Query<MovieSession> query = session.createQuery(hqlQuery, MovieSession.class);

            LocalDate startTime = date.atStartOfDay().toLocalDate();
            LocalDate endTime = date.plusDays(1).atStartOfDay().toLocalDate();

            query.setParameter("movieId", movieId);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to retrieve available MovieSessions "
                                        + "for movieId: " + movieId + " and date: " + date, e);
        }
    }
}
