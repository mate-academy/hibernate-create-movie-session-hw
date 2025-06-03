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
            throw new DataProcessingException("Can't insert movie session entity "
                    + movieSession, e);
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
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MovieSession", MovieSession.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);
            return session.createQuery(
                            "FROM MovieSession ms "
                                    + "LEFT JOIN FETCH ms.movie "
                                    + "LEFT JOIN FETCH ms.cinemaHall "
                                    + "WHERE ms.movie.id = :movieId "
                                    + "AND ms.showTime BETWEEN :start AND :end "
                                    + "ORDER BY ms.showTime ASC", MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("start", startOfDay)
                    .setParameter("end", endOfDay)
                    .list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions of movie Id: "
                    + movieId
                    + "on the date:"
                    + date, e);
        }
    }
}
