package mate.academy.dao.impl;

import java.time.LocalDate;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery =
                    session.createQuery("from MovieSession ms "
                            + "where date(showTime) = :day "
                            + "and ms.showTime >= :startOfDay "
                            + "and ms.showTime <= :endOfDay "
                            + "and ms.id = :movieID", MovieSession.class);
            getAllMovieSessionsQuery.setParameter("day", date);
            getAllMovieSessionsQuery.setParameter("startOfDay", date.atStartOfDay());
            getAllMovieSessionsQuery.setParameter("endOfDay", date.atTime(23, 59, 59));
            getAllMovieSessionsQuery.setParameter("movieID", movieId);
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions from DB", e);
        }
    }
}
