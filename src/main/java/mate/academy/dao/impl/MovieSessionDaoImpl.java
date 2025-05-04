package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot save a movieSession " + movieSession, e);
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
            throw new DataProcessingException("Cannot get a movieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> list = new ArrayList<>();
        LocalDateTime startOfTheDay = date.atStartOfDay();
        LocalDateTime endOfTheDay = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> queryOfMovieSession = session.createQuery(
                    "FROM MovieSession m "
                            + "LEFT JOIN FETCH m.movie "
                            + "WHERE m.id = :id "
                            + "AND m.showTime >= :start "
                            + "AND m.showTime <= :end "
                            + "ORDER BY m.showTime", MovieSession.class);
            queryOfMovieSession.setParameter("id", movieId);
            queryOfMovieSession.setParameter("start", startOfTheDay);
            queryOfMovieSession.setParameter("end", endOfTheDay);

            MovieSession movieSession = queryOfMovieSession.uniqueResult();
            list.add(movieSession);
            return list;
        } catch (Exception e) {
            throw new DataProcessingException("Cannot find movie by id " + movieId
                    + "at the day " + date, e);
        }

    }
}
