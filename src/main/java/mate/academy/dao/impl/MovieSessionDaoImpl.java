package mate.academy.dao.impl;

import java.time.LocalDate;
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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't to add a movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN fetch ms.movie "
                            + "LEFT JOIN fetch ms.cinemaHall "
                            + "WHERE ms.id = :id", MovieSession.class);
            movieSessionQuery.setParameter("id", id);
            return movieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN fetch ms.movie "
                            + "LEFT JOIN fetch ms.cinemaHall "
                            + "WHERE ms.movie.id = :movieId "
                            + "AND year(ms.showTime) = :year "
                            + "AND month(ms.showTime) = :month "
                            + "AND day(ms.showTime) = :day",
                    MovieSession.class);
            movieSessionQuery.setParameter("movieId", movieId);
            movieSessionQuery.setParameter("year", date.getYear());
            movieSessionQuery.setParameter("month", date.getMonthValue());
            movieSessionQuery.setParameter("day", date.getDayOfMonth());
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie sessions", e);
        }
    }
}
