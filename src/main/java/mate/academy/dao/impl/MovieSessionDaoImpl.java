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
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            //session.save(movieSession);
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session: " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableMovieSessionsByMovieAndDate = session.createQuery(
                    "FROM MovieSession ms JOIN FETCH ms.movie m JOIN FETCH ms.cinemaHall "
                            + "WHERE m.id = :movieId AND ms.showTime BETWEEN :startOfDay "
                            + "AND :endOfDay", MovieSession.class);
            availableMovieSessionsByMovieAndDate.setParameter("movieId", movieId);
            availableMovieSessionsByMovieAndDate.setParameter("startOfDay",
                    LocalDateTime.of(date, LocalTime.MIN));
            availableMovieSessionsByMovieAndDate.setParameter("endOfDay",
                    LocalDateTime.of(date, LocalTime.MAX));
            return availableMovieSessionsByMovieAndDate.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions", e);
        }
    }
}
