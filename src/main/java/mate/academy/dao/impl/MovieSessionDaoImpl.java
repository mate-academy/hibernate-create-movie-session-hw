package mate.academy.dao.impl;

import java.time.LocalDate;
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
            throw new DataProcessingException(
                    "Can`t add Movie Session: " + movieSession, e);
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
            throw new DataProcessingException(
                    "Can't get a Movie Session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findSessions = session.createQuery(
                    "from MovieSession ms WHERE ms.id = :id "
                            + "AND YEAR(ms.showTime) = :year "
                            + "AND MONTH(ms.showTime) = :month "
                            + "AND DAY(ms.showTime) = :day", MovieSession.class);
            findSessions.setParameter("id", movieId);
            findSessions.setParameter("year", date.getYear());
            findSessions.setParameter("month", date.getMonthValue());
            findSessions.setParameter("day", date.getDayOfMonth());
            return findSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get all Movie Session with id: " + movieId
                            + " date " + date, e);
        }
    }
}
