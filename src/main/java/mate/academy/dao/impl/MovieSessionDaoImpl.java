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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't save movie session: " + movieSession + " to DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM MovieSession mc "
                    + "LEFT JOIN FETCH mc.movie"
                    + " LEFT JOIN FETCH mc.cinemaHall "
                    + "WHERE mc.id = :id", MovieSession.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get movie session for id: " + id + " from DB!", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableMovieSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MovieSession mc "
                    + "LEFT JOIN FETCH mc.movie m "
                    + "LEFT JOIN FETCH mc.cinemaHall c "
                    + "WHERE m.id = :id "
                    + "AND mc.showTime BETWEEN :start AND :end ", MovieSession.class)
                    .setParameter("id", movieId)
                    .setParameter("start", date.atStartOfDay())
                    .setParameter("end", date.atTime(LocalTime.MAX))
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get all movie sessions for id: " + movieId + " and date: " + date, e);
        }
    }
}
