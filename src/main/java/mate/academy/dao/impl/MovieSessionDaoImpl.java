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
            throw new DataProcessingException("Can't add movieSession: " + movieSession, e);
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
            throw new DataProcessingException("Can't get MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfTheDay = date.atTime(LocalTime.MIN);
        LocalDateTime endOfTheDay = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM MovieSession AS mv WHERE mv.id = :id "
                    + "AND mv.showTime BETWEEN :startOfTheDay AND :endOfTheDay";
            Query<MovieSession> sessionQuery = session.createQuery(query, MovieSession.class);
            sessionQuery.setParameter("id", movieId);
            sessionQuery.setParameter("startOfTheDay", startOfTheDay);
            sessionQuery.setParameter("endOfTheDay", endOfTheDay);
            return sessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find sessions by id: "
                    + movieId + " , date: " + date, e);
        }
    }
}
