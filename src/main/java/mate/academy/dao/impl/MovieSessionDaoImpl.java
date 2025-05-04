package mate.academy.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save MovieSession "
                    + movieSession + "to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession by id" + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId,
                LocalDateTime startDate, LocalDateTime finishDate) {
        try (Session session = sessionFactory.openSession()) {
            String findAvailableSessions = "FROM MovieSession ms WHERE ms.movie.id = :movieID "
                    + "AND ms.showTime BETWEEN :startDate AND :finishDate";
            Query<MovieSession> findAvailableSessionsQuery =
                    session.createQuery(findAvailableSessions, MovieSession.class);
            findAvailableSessionsQuery.setParameter("movieID", movieId);
            findAvailableSessionsQuery.setParameter("startDate", startDate);
            findAvailableSessionsQuery.setParameter("finishDate", finishDate);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get All MovieSessions from DB", e);
        }
    }
}
