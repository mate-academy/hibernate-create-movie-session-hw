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
            throw new DataProcessingException("Can't insert movie session: " + movieSession, e);
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
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableMovieSession =
                    session.createQuery("FROM MovieSession m WHERE m.movie.id = :movieId"
                            + "AND m.showTime BETWEEN :startOfDay AND :endOfDay",
                            MovieSession.class);
            findAvailableMovieSession.setParameter("movieId", movieId);
            findAvailableMovieSession.setParameter("startOfDay",
                    LocalDateTime.of(localDate, LocalTime.MIN));
            findAvailableMovieSession.setParameter("endOfDay",
                    LocalDateTime.of(localDate, LocalTime.MAX));
            return findAvailableMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available session at: " + localDate
            + "by movie id" + movieId, e);
        }
    }
}
