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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't add MovieSession to db: " + movieSession, e);
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
            throw new DataProcessingException("can't get MovieSession from db by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAvailableSession(Long movieId, LocalDate date) {
        String getAllMovieSessionsQuery = "FROM MovieSession ms "
                + "LEFT JOIN FETCH ms.movie m "
                + "WHERE m.id = :movieId AND (ms.showTime BETWEEN :startOfDay AND :endOfDay)";
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getSessionQuery =
                    session.createQuery(getAllMovieSessionsQuery, MovieSession.class);
            getSessionQuery.setParameter("movieId", movieId);
            getSessionQuery.setParameter("startOfDay", startOfDay);
            getSessionQuery.setParameter("endOfDay", endOfDay);
            return getSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions from db", e);
        }
    }
}
