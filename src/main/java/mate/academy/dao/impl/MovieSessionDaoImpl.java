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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can`t insert movie session " + movieSession, e);
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
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can`t get movie session by this id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionInDate = session.createQuery(
                    "FROM MovieSession mc "
                            + "WHERE mc.movie = (FROM Movie m WHERE m.id = :movie_id) "
                            + "AND mc.showTime BETWEEN :beginDay AND :endDay",
                    MovieSession.class);
            getAllAvailableSessionInDate.setParameter("beginDay", date.atTime(0, 0, 0));
            getAllAvailableSessionInDate.setParameter("endDay", date.atTime(23, 59, 59));
            getAllAvailableSessionInDate.setParameter("movie_id", movieId);
            return getAllAvailableSessionInDate.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can`t get all movie sessions where movie id "
                    + movieId + " and date " + date, e);
        }
    }
}
