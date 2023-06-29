package mate.academy.dao.movie.session.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.movie.session.MovieSessionDao;
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
            throw new DataProcessingException("Can't add MovieSession: "
                    + movieSession + " to DB.", e);
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
            Query<MovieSession> movieSessionQuery = session.createQuery(
                    "from MovieSession ms left join fetch ms.cinemaHall "
                            + "left join fetch ms.movie where ms.id = :id", MovieSession.class);
            movieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(movieSessionQuery.uniqueResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession with id: "
                    + id + " from DB.", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessions = session.createQuery(
                    "from MovieSession ms left join fetch ms.cinemaHall "
                            + "left join fetch ms.movie where ms.movie.id = :id "
                            + "and YEAR(ms.showTime) = :year "
                            + "and MONTH(ms.showTime) = :month and DAY(ms.showTime) = :day ",
                    MovieSession.class);
            movieSessions.setParameter("id", movieId);
            movieSessions.setParameter("year", date.getYear());
            movieSessions.setParameter("month", date.getMonthValue());
            movieSessions.setParameter("day", date.getDayOfMonth());
            return movieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available MovieSession with id: " + movieId
                    + " and date: " + date + " from DB", e);
        }
    }
}
