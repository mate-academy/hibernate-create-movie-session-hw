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
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't save movie session "
                    + movieSession + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM MovieSession ms"
                    + "LEFT JOIN FETCH ms.movie"
                    + "LEFT JOIN FETCH ms.cinemaHall"
                    + "WHERE ms.id = :id", MovieSession.class)
            .setParameter("id", id)
            .uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.movie m "
                    + "LEFT JOIN FETCH ms.cinemaHall "
                    + "WHERE m.id = :id "
                    + "AND ms.showTime BETWEEN :start AND :end ", MovieSession.class)
                    .setParameter("id", movieId)
                    .setParameter("start", date.atStartOfDay())
                    .setParameter("end", date.atTime(LocalTime.MAX))
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions by id:"
                    + movieId + "and date:" + date, e);
        }
    }
}
