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
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can not save MovieSession to DB. "
                    + movieSession, e);
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
        } catch (Exception ex) {
            throw new DataProcessingException("Can not get MovieSessiom by id. id: " + id, ex);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM MovieSession ms "
                    + "WHERE ms.movie.id = :movieId "
                    + "AND ms.showTime BETWEEN :startOfDay AND :endOfDay";

            Query<MovieSession> getMovieSessionListQuery
                    = session.createQuery(query, MovieSession.class);

            getMovieSessionListQuery.setParameter("movieId", movieId);
            getMovieSessionListQuery.setParameter("startOfDay", startOfDay);
            getMovieSessionListQuery.setParameter("endOfDay", endOfDay);

            return getMovieSessionListQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of MovieSessions"
                    + " by movieId and date. "
                    + "movieId: " + movieId + ", "
                    + "date: " + date, e);
        }
    }
}
