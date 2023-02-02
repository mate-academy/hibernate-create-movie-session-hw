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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
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
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getMovieSessionQuery = session.createQuery("FROM MovieSession ms "
                    + "WHERE ms.id = :id");
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable((MovieSession) getMovieSessionQuery.uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionsByDate = session.createQuery(
                    "FROM MovieSession ms "
                    + "WHERE ms.showTime BETWEEN :from AND :to "
                    + "AND ms.movie.id = :movieId", MovieSession.class);
            getMovieSessionsByDate.setParameter(
                    "from", LocalDateTime.of(date, LocalTime.of(0,0)));
            getMovieSessionsByDate.setParameter(
                    "to", LocalDateTime.of(date,LocalTime.of(23, 59, 59)));
            getMovieSessionsByDate.setParameter("movieId", movieId);
            return getMovieSessionsByDate.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find movie session for movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
