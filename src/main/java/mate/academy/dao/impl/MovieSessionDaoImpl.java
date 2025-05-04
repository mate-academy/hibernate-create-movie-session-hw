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
import org.hibernate.SessionFactory;
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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        LocalDateTime startOfDate = date.atStartOfDay();
        LocalDateTime endOfDate = date.atTime(LocalTime.MAX);
        try (Session session = factory.openSession()) {
            Query<MovieSession> movieSessionQuery =
                    session.createQuery("FROM MovieSession ms "
                                    + "LEFT JOIN FETCH ms.movie "
                                    + "LEFT JOIN FETCH ms.cinemaHall "
                                    + "WHERE ms.movie.id = :movieId "
                                    + "AND ms.showTime >= :startTime AND ms.showTime <= :endTime",
                            MovieSession.class);
            movieSessionQuery.setParameter("movieId", movieId);
            movieSessionQuery.setParameter("startTime", startOfDate);
            movieSessionQuery.setParameter("endTime", endOfDate);
            return movieSessionQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not get all movieSessions for movie with id "
                    + movieId + " from DB for date " + date, e);
        }
    }
}
