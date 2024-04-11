package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't save movie session: " + movieSession + " to the DB", e);
        }
        return null;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MovieSession movieSession = session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session: " + id, e);
        }
        return null;
    }

    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Movie movie = session.get(Movie.class, movieId);
            Query<MovieSession> movieSessionQuery = session.createQuery(
                    "SELECT ms FROM MovieSession ms "
                    + "JOIN fetch ms.movie "
                    + "WHERE ms.movie = :movie "
                    + " AND ms.showTime BETWEEN :from AND :to ", MovieSession.class);
            movieSessionQuery.setParameter("movie", movie);
            movieSessionQuery.setParameter("from", from);
            movieSessionQuery.setParameter("to", to);
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie sessions for movie ID: "
                    + movieId + " and date: " + date, e);
        }
    }
}
