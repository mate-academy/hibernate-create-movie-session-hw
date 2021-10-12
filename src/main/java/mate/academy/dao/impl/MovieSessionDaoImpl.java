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
            throw new DataProcessingException("Can't insert movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "FROM MovieSession "
                    + "LEFT JOIN FETCH MovieSession.cinemaHall "
                    + "LEFT JOIN FETCH MovieSession.movie "
                    + "WHERE MovieSession.id = :session_id";
            Query<MovieSession> query = session.createQuery(queryString, MovieSession.class);
            query.setParameter("session_id", id);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.cinemaHall "
                    + "LEFT JOIN FETCH ms.movie "
                    + "WHERE ms.movie.id = :movie_id AND DATE(ms.showTime) = :date";
            Query<MovieSession> query = session.createQuery(queryString, MovieSession.class);
            query.setParameter("movie_id", movieId);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions for movie with ID: "
                    + movieId + " and date: " + localDate, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryString = "FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.cinemaHall "
                    + "LEFT JOIN FETCH ms.movie";
            return session.createQuery(queryString, MovieSession.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions", e);
        }
    }
}
