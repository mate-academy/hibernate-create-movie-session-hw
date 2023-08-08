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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add movie session: "
                    + movieSession + " to DB.", e
            );
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get a movie session from DB by id: " + id, e
            );
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String findAvailableSessionsHql = "FROM MovieSession ms "
                + "WHERE ms.showTime BETWEEN :startOfDay AND :endOfDay "
                + "AND ms.movie.id = :movieId";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> findAllAvailableSessionsQuery =
                    session.createQuery(findAvailableSessionsHql, MovieSession.class);
            findAllAvailableSessionsQuery.setParameter("startOfDay", date.atStartOfDay());
            findAllAvailableSessionsQuery.setParameter("endOfDay", date.atTime(LocalTime.MAX));
            findAllAvailableSessionsQuery.setParameter("movieId", movieId);
            return findAllAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get sessions by movie id: " + movieId
                            + " and by date: " + date, e
            );
        }
    }
}
