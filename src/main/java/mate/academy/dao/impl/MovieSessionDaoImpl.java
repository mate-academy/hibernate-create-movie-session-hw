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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t insert movieSession: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get movieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery = session.createQuery(
                    "FROM MovieSession ms "
                    + "JOIN FETCH ms.movie m"
                    + "JOIN FETCH ms.cinemaHall"
                    + "WHERE EXTRACT(YEAR FROM ms.showTime) = :year "
                    + "AND EXTRACT(MONTH FROM ms.showTime) = :month "
                    + "AND EXTRACT(DAY FROM ms.showTime) = :day"
                    + "AND m.id = :movieId;", MovieSession.class);
            getAllAvailableSessionsQuery.setParameter("year", date.getYear());
            getAllAvailableSessionsQuery.setParameter("month", date.getMonth());
            getAllAvailableSessionsQuery.setParameter("day", date.getDayOfMonth());
            getAllAvailableSessionsQuery.setParameter("movieId", movieId);
            return getAllAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get all available sessions for date: "
                    + date, e);
        }
    }
}
