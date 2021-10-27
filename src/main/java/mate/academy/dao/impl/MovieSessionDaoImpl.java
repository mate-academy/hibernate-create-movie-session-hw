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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save movie session with param "
                    + movieSession + ". ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by ID "
                    + id + ". ", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "FROM MovieSession ms"
                + " left  join  fetch ms.movie m"
                + " WHERE m.id = :movieId"
                + " AND ms.showTime BETWEEN :start AND :end";
        try (Session session = factory.openSession()) {
            Query queryResult = session.createQuery(query);
            queryResult.setParameter("movieId", movieId);
            queryResult.setParameter("start", LocalDateTime.of(date, LocalTime.MIN));
            queryResult.setParameter("end", LocalDateTime.of(date, LocalTime.MAX));
            return queryResult.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions with movie ID "
                    + movieId + ". ", e);
        }
    }
}
