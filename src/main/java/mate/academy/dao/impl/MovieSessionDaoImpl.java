package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessions = session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.id = :id",
                    MovieSession.class);
            movieSessions.setParameter("id", id);
            return movieSessions.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableSessions = session.createQuery(
                    "from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.movie.id = :id "
                            + "and year(ms.showTime) = :year "
                            + "and month(ms.showTime) = :month "
                            + "and day(ms.showTime) = :day",
                    MovieSession.class);
            availableSessions.setParameter("id", movieId);
            availableSessions.setParameter("year", date.getYear());
            availableSessions.setParameter("month", date.getMonthValue());
            availableSessions.setParameter("day", date.getDayOfMonth());
            return availableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions", e);
        }
    }
}
