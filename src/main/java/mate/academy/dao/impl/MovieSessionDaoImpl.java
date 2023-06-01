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
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can not insert a movie session! "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MovieSession movieSessionFromDB = new MovieSession();
            Query<MovieSession> getMovieSessionQuery
                    = session.createQuery("from MovieSession m "
                    + "left join fetch m.cinemaHall "
                    + "left join fetch m.movie"
                    + " where m.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            movieSessionFromDB = getMovieSessionQuery.getSingleResult();
            return Optional.ofNullable(movieSessionFromDB);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get a movie session by id! " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.of(23, 59, 59));
            Query<MovieSession> findAvailableSessionQuery
                    = session.createQuery("from MovieSession m "
                    + "left join fetch m.cinemaHall "
                    + "left join fetch m.movie "
                    + "where m.id = :id "
                    + "and m.showTime >= :startTime and m.showTime <= :endTime");
            findAvailableSessionQuery.setParameter("id", movieId);
            findAvailableSessionQuery.setParameter("startTime", startTime);
            findAvailableSessionQuery.setParameter("endTime", endTime);
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not find available sessions with movie id "
                    + movieId + " and date " + date, e);
        }
    }
}
