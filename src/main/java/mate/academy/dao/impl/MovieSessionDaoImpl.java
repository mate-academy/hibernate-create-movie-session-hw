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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory;

    public MovieSessionDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add a movie session: " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms "
                            + "where ms.movie.id = :movieId and "
                            + "ms.showTime between :startTime and :endTime",
                    MovieSession.class);
            getAvailableSessionsQuery.setParameter("movieId", movieId);
            getAvailableSessionsQuery.setParameter("startTime", date.atTime(LocalTime.MIN));
            getAvailableSessionsQuery.setParameter("endTime", date.atTime(LocalTime.MAX));
            return getAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of movie sessions", e);
        }
    }
}
