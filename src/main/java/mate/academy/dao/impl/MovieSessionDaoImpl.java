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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session "
                    + movieSession + " to DB.", e);
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
            throw new DataProcessingException("Can't get movie session with id: "
                    + id + " from DB.", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findSessionsQuery = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :movieId "
                    + "and ms.localDateTime >= :dateMin "
                    + "and ms.localDateTime <= :dateMax", MovieSession.class);
            findSessionsQuery.setParameter("movieId", movieId);
            findSessionsQuery.setParameter("dateMin", date.atStartOfDay());
            findSessionsQuery.setParameter("dateMax", LocalDateTime.of(date, LocalTime.MAX));
            return findSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all available sessions by date: "
                    + date + " and movie id: " + movieId, e);
        }
    }
}
