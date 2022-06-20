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
            throw new DataProcessingException("Can't insert movie session" + movieSession, e);
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
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfTheDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfTheDay = LocalDateTime.of(date, LocalTime.MAX);
        String queryHql = "from MovieSession AS ms "
                + "LEFT JOIN FETCH ms.movie "
                + "WHERE ms.movie.id = :id "
                + "AND ms.showTime BETWEEN :startOfTheDay AND :endOfTheDay";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(queryHql, MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("startOfTheDay", startOfTheDay);
            query.setParameter("endOfTheDay", endOfTheDay);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions by date: " + date
                    + " and by movie id " + movieId, e);
        }
    }
}
