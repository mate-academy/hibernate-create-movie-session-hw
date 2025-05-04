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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessionsQuery
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "where m.id = :movieId "
                    + "and ms.showTime between :startOfDay and :endOfDay", MovieSession.class);
            getAvailableSessionsQuery.setParameter("movieId", movieId);
            LocalDateTime startOfDay
                    = LocalDateTime.of(date, LocalTime.MIN);
            getAvailableSessionsQuery.setParameter("startOfDay", startOfDay);
            LocalDateTime endOfDay
                    = LocalDateTime.of(date, LocalTime.MAX);
            getAvailableSessionsQuery.setParameter("endOfDay", endOfDay);
            return getAvailableSessionsQuery.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't find available movie session with "
                    + " movie id " + movieId + " and date " + date, e);
        }
    }
}
