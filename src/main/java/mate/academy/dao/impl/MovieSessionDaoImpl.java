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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime beginOfDay = LocalTime.MIN.atDate(date);
        LocalDateTime endOfDay = LocalTime.MAX.atDate(date);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessions = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie m "
                            + "WHERE m.id = :movieIdSession "
                            + "AND ms.showTime BETWEEN :beginOfDay AND :endOfDay",
                    MovieSession.class);
            findAvailableSessions.setParameter("movieIdSession", movieId);
            findAvailableSessions.setParameter("beginOfDay", beginOfDay);
            findAvailableSessions.setParameter("endOfDay", endOfDay);
            return findAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions", e);
        }
    }
}
