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
            throw new DataProcessingException("Can't insert Movie Session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery("from MovieSession m "
                    + "left join fetch m.movie "
                    + "left join fetch m.cinemaHall "
                    + "where m.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get Movie Session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAvailableMovieSessionsQuery =
                    session.createQuery("from MovieSession m "
                            + "left join fetch m.movie "
                            + "left join fetch m.cinemaHall "
                            + "where m.movie.id =:movieId "
                            + "and m.showTime between :fromDate and :toDate", MovieSession.class);
            getAvailableMovieSessionsQuery.setParameter("movieId", movieId);
            getAvailableMovieSessionsQuery.setParameter("fromDate", date.atStartOfDay());
            getAvailableMovieSessionsQuery.setParameter("toDate", date.atTime(LocalTime.MAX));
            return getAvailableMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available Movie Sessions for movie id: "
                    + movieId + " on date: " + date, e);
        }
    }
}
