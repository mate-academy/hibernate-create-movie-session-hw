package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
            throw new DataProcessingException("Couldn't add movie session: " + movieSession, e);
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
            throw new DataProcessingException("Couldn't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        String query = "FROM MovieSession";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> sessionQuery = session.createQuery(query, MovieSession.class);
            return sessionQuery.getResultList();
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate localDate) {
        String query = "FROM MovieSession ms LEFT JOIN FETCH ms.movie "
                + "WHERE ms.movie.id = :movie_id "
                + "AND DATE(ms.showTime) = :date";
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> sessionQuery = session.createQuery(query, MovieSession.class);
            sessionQuery.setParameter("movie_id", movieId);
            sessionQuery.setParameter("date", date);
            return sessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't find available sessions by movie id: "
                    + movieId + " and by localDate: " + localDate, e);
        }
    }
}
