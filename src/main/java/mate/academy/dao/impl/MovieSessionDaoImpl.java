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
            throw new DataProcessingException("Can't insert a movie session " + movieSession, e);
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
    public List<MovieSession> findAvailableSessions(Long id, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM MovieSession "
                    + "WHERE movie.id = :id "
                    + "AND localDateTime BETWEEN :timeFrom AND :timeTo";
            Query<MovieSession> findAvailableSession
                    = session.createQuery(query, MovieSession.class);
            findAvailableSession.setParameter("id", id);
            findAvailableSession.setParameter("timeFrom", LocalTime.MIN.atDate(date));
            findAvailableSession.setParameter("timeTo", LocalTime.MAX.atDate(date));
            return findAvailableSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find an available session by movie id: "
                    + id + " and date: " + date, e);
        }
    }
}
