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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can not save movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session
                    .createQuery("FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie as m "
                            + "LEFT JOIN FETCH ms.cinemaHall as cH "
                            + "WHERE ms.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionQuery.uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Movie session with id = " + id + " does not exist", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessionsQuery = session
                    .createQuery("FROM MovieSession ms "
                                    + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                                    + "LEFT JOIN FETCH ms.movie AS m "
                                    + "WHERE ms.movie.id = :movieId "
                                    + "AND ms.dateTime BETWEEN :startDate AND  :endDate",
                            MovieSession.class);
            getAvailableSessionsQuery.setParameter("movieId", movieId);
            getAvailableSessionsQuery.setParameter("startDate", date.atStartOfDay());
            getAvailableSessionsQuery.setParameter("endDate", date.atTime(LocalTime.MAX));
            return getAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get movie session by date " + date, e);
        }
    }
}
