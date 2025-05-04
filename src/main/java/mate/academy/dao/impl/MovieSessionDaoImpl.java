package mate.academy.dao.impl;

import java.time.LocalDate;
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
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.movie m WHERE m.id = :id "
                    + "AND ms.showTime > :dayBefore "
                    + "AND ms.showTime < :dayAfter", MovieSession.class);
            LocalDateTime dayBefore = date.atTime(0, 0);
            LocalDateTime dayAfter = dayBefore.plusDays(1L);
            query.setParameter("id", movieId);
            query.setParameter("dayBefore", dayBefore);
            query.setParameter("dayAfter", dayAfter);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get all movie sessions", e);
        }
    }
}
