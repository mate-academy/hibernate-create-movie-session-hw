package mate.academy.dao.impl;

import java.time.LocalDateTime;
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
    public List<MovieSession> findMovieSessionsByMovieIdAndTime(Long movieId,
                                                                LocalDateTime timeFrom,
                                                                LocalDateTime timeTo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findMovieSessionsQuery = session.createQuery(
                    "from MovieSession ms where ms.movie.id = :movieId "
                            + "and ms.showTime between :startTime and :endTime",
                    MovieSession.class);
            findMovieSessionsQuery.setParameter("movieId", movieId);
            findMovieSessionsQuery.setParameter("startTime", timeFrom);
            findMovieSessionsQuery.setParameter("endTime", timeTo);
            return findMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find movie sessions by movie id: "
                    + movieId + " and time from: " + timeFrom + " to: " + timeTo, e);
        }
    }
}
