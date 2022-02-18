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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert CinemaHall.", e);
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
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionQuery
                    = session.createQuery("FROM MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "left join fetch ms.cinemaHall "
                    + "WHERE m.id = :movieId "
                    + "AND ms.showTime BETWEEN :dayBegin AND :dayEnd", MovieSession.class);
            getAllMovieSessionQuery.setParameter("movieId", movieId);
            getAllMovieSessionQuery.setParameter("dayBegin", date.atStartOfDay());
            getAllMovieSessionQuery.setParameter("dayEnd", date.atTime(LocalTime.MAX));
            return getAllMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all MovieSessions.", e);
        }
    }
}
