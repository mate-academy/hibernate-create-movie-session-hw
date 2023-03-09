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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Couldn't add movie session "
                    + movieSession + " to DB", e);
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
            throw new DataProcessingException("Couldn't add movie session by id: "
                    + id + " to DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionInCurrentDay =
                    session.createQuery("FROM MovieSession m WHERE m.showtime "
                                    + "BETWEEN :fromDateTime AND :toDateTime "
                                    + "AND m.movie.id = :movieId",
                            MovieSession.class);
            getAllMovieSessionInCurrentDay.setParameter("fromDateTime", date.atStartOfDay());
            getAllMovieSessionInCurrentDay.setParameter("toDateTime", date.atTime(LocalTime.MAX));
            getAllMovieSessionInCurrentDay.setParameter("movieId", movieId);
            return getAllMovieSessionInCurrentDay.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't find movie session by movie id: "
                    + movieId + " and at the date: " + date, e);
        }
    }
}
