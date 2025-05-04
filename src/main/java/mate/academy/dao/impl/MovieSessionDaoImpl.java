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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
        final LocalTime endOfDay = LocalTime.of(23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "WHERE ms.showTime > :startOfDay AND ms.showTime <= :endOfDay "
                    + "AND m.id = :movieId", MovieSession.class);
            query.setParameter("startOfDay", date.atStartOfDay());
            query.setParameter("endOfDay", date.atTime(endOfDay));
            query.setParameter("movieId", movieId);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions by movie id: "
                    + movieId + ", for date:" + date, e);
        }
    }
}
