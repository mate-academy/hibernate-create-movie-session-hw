package mate.academy.dao.impl;

import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.getTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession " + movieSession + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession by ID " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String query = "FROM " + MovieSession.class.getName() + " ms "
                + "JOIN FETCH ms.movie "
                + "JOIN FETCH ms.cinemaHall "
                + "WHERE ms.movie.id = :movieId "
                + "AND DATE(ms.showTime) = :date "
                + "AND TIME(ms.showTime) > :currentTime";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(query, MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("data", date)
                    .setParameter("currentTime", currentDateTime.toLocalTime())
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions", e);
        }
    }
}
