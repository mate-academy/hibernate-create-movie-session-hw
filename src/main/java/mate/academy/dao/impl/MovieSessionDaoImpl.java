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
    private static final String START_OF_TIME_FIELD = "startOfDay";
    private static final String END_OF_TIME_FIELD = "endOfDay";

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert a MovieSession: " + movieSession, e);
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
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        String hql = "FROM MovieSession";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessions = session.createQuery(hql, MovieSession.class);
            return getAllMovieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all MovieSessions!", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String hql = "FROM MovieSession m "
                + "LEFT JOIN FETCH m.cinemaHall "
                + "LEFT JOIN FETCH m.movie "
                + "WHERE m.showTime > :startOfDay "
                + "AND m.showTime < :endOfDay";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessions = session.createQuery(hql,
                    MovieSession.class);
            getAllAvailableSessions.setParameter(START_OF_TIME_FIELD,
                    date.atStartOfDay());
            getAllAvailableSessions.setParameter(END_OF_TIME_FIELD,
                    date.atTime(LocalTime.MAX));
            return getAllAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any MovieSession by params: "
                    + movieId + " and " + date, e);
        }
    }
}
