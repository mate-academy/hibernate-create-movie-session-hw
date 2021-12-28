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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add movie session to DB. " + movieSession, e);
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
            throw new DataProcessingException("Can`t get movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startSearchTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endSearchTime = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery =
                    session.createQuery("FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "WHERE ms.movie.id = :movieId "
                            + "AND ms.showTime BETWEEN :start AND :end",
                            MovieSession.class);
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            findAvailableSessionsQuery.setParameter("start", startSearchTime);
            findAvailableSessionsQuery.setParameter("end", endSearchTime);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get available sessions by the movie Id:"
                    + movieId + " and date: " + date, e);
        }
    }
}
