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
            session.persist(movieSession);
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.atTime(23, 59, 59);

            String hql = "FROM MovieSession s "
                    + "LEFT JOIN FETCH s.movie m "
                    + "WHERE m.id = :id "
                    + "AND s.localDateTime >= :startDate "
                    + "AND s.localDateTime <= :endDate";

            Query<MovieSession> query = session.createQuery(hql, MovieSession.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("id", movieId);

            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get sessions", e);
        }
    }
}
