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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime timeFrom = date.atStartOfDay();
        LocalDateTime timeTo = date.atTime(23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query =
                    session.createQuery(
                            "from MovieSession ms "
                                    + "where ms.id = :movieId "
                                    + "and ms.showTime > :timeFrom "
                                    + "and ms.showTime < :timeTo order by ms.showTime",
                            MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("timeFrom", timeFrom);
            query.setParameter("timeTo", timeTo);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get movie sessions for date " + date + " and movie id " + movieId, e);
        }
    }
}
