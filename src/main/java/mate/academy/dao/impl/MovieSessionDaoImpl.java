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
            throw new DataProcessingException("Can't insert movie session "
                    + movieSession + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session
                     = HibernateUtil.getSessionFactory().openSession()) {
            MovieSession movieSession = session.get(MovieSession.class, id);
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startSessions = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endSession = LocalDateTime.of(date,LocalTime.MAX);
        try (Session session
                     = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MovieSession ms "
                            + "WHERE ms.id = :id AND ms.showTime "
                            + "BETWEEN :openSession AND :closeSession")
                            .setParameter("id", movieId)
                            .setParameter("openSession", startSessions)
                            .setParameter("closeSession", endSession)
                            .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list movie sessions from DB", e);
        }
    }
}
