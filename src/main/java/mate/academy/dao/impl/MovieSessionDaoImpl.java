package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
            throw new DataProcessingException("Can't add movie session: " + movieSession + " to DB!", e);
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
            throw new DataProcessingException("Can't find movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAllByMovieIdAndDate(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllByMovieIdAndDateQuery = session.createQuery("from MovieSession"
                    + "where movie_id = :movieId"
                    + "AND showTime BETWEEN :beginOfDay AND :endOfDay", MovieSession.class);
            getAllByMovieIdAndDateQuery.setParameter("movieId", movieId);
            getAllByMovieIdAndDateQuery.setParameter("beginOfDay",localDate.atStartOfDay());
            getAllByMovieIdAndDateQuery.setParameter("endOfDay", localDate.atTime(23,59,59));
            return getAllByMovieIdAndDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all movie sessions by movieId: "
                    + movieId + " and date: " + localDate + " from DB. ", e);
        }
    }
}
