package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            throw new DataProcessingException("Can`t add movie session " + movieSession, e);
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
            throw new DataProcessingException("Can`t get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "select m from MovieSession m where m.movie.id = :movieId "
                + "and m.showTime between :startTime and :finishTime";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(query, MovieSession.class);
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime finishTime = LocalDateTime.of(date, LocalTime.MAX);
            movieSessionQuery.setParameter("movieId", movieId);
            movieSessionQuery.setParameter("startTime", startTime);
            movieSessionQuery.setParameter("finishTime", finishTime);
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can`t find movies session by id " + movieId + " and date " + date, e);
        }
    }
}
