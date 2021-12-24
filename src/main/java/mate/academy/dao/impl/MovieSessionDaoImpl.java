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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
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
        try (Session session = factory.openSession()) {
            Query<MovieSession> getMovieSessionById = session
                    .createQuery("FROM MovieSession ms"
                            + " join fetch ms.movie"
                            + " join fetch ms.cinemaHall"
                            + " WHERE ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> findAvailableSessions = session
                    .createQuery("FROM MovieSession ms"
                            + " join fetch ms.movie"
                            + " join fetch ms.cinemaHall"
                            + " WHERE ms.movie.id = :movieId"
                            + " AND ms.showTime BETWEEN :startOfDay "
                            + " AND :endOfDay", MovieSession.class);
            findAvailableSessions.setParameter("movieId", movieId);
            findAvailableSessions.setParameter("startOfDay", date.atTime(LocalTime.MIN));
            findAvailableSessions.setParameter("endOfDay", date.atTime(LocalTime.MAX));
            return findAvailableSessions.getResultList();

        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions by movie id: "
                    + movieId + " and by date: " + date, e);
        }
    }
}
