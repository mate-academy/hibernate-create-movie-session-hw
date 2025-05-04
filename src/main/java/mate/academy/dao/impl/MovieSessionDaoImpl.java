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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime dateTimeFrom = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime dateTimeTo = LocalDateTime.of(date, LocalTime.MAX);
            Query<MovieSession> query = session.createQuery(
                    "FROM MovieSession as ms "
                    + "JOIN FETCH ms.movie as m "
                    + "JOIN FETCH ms.cinemaHall "
                    + "WHERE m.id = :movieId "
                    + "AND ms.showTime BETWEEN :dateTimeFrom AND :dateTimeTo", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("dateTimeFrom", dateTimeFrom);
            query.setParameter("dateTimeTo", dateTimeTo);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie with id: " + movieId
                    + " on " + date, e);
        }
    }
}
