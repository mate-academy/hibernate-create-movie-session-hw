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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session object " + movieSession, e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session with id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String hqlQuery = "from MovieSession where movie.id=:movieId "
                + "and showTime > :beginningOfDay and showTime < :endingOfDay";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(hqlQuery, MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("beginningOfDay", LocalDateTime.of(date, LocalTime.MIN));
            query.setParameter("endingOfDay", LocalDateTime.of(date, LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all movie sessions from db", e);
        }
    }
}
