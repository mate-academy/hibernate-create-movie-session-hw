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
import org.hibernate.HibernateException;
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
        } catch (HibernateException e) {
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
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> allAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms "
                            + "where ms.movie.id = :movieId "
                            + "and ms.showTime between :startTime "
                            + "and :endTime",
                    MovieSession.class);
            allAvailableSessionsQuery.setParameter("movieId", movieId);
            allAvailableSessionsQuery.setParameter("startTime", date.atTime(LocalTime.MIN));
            allAvailableSessionsQuery.setParameter("endTime", date.atTime(LocalTime.MAX));
            return allAvailableSessionsQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(
                    "Can't get movie sessions by id: " + movieId
                            + " on " + date + " from DB", e);
        }
    }
}
