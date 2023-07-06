package mate.academy.dao.impl;

import java.sql.Date;
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
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add MovieSession", e);
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
            Query<MovieSession> findSessionQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "LEFT JOIN FETCH ms.movie "
                            + "WHERE ms.movie.id = :movieId", MovieSession.class);
            findSessionQuery.setParameter("movieId", id);
            return Optional.ofNullable(findSessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "LEFT JOIN FETCH ms.movie "
                            + "WHERE ms.movie.id = :movieId "
                            + "AND DATE (ms.showTime) = :date", MovieSession.class);
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            findAvailableSessionsQuery.setParameter("date", Date.valueOf(date));
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id " + movieId
                    + " and date " + date, e);
        }
    }
}
