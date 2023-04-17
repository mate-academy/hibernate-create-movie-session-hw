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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery =
                    session.createQuery("from MovieSession ms"
                    + " left join fetch ms.movie"
                    + " left join fetch ms.cinemaHall"
                    + " where ms.id = :id");
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionQuery =
                    session.createQuery("from MovieSession ms"
                    + " left join fetch ms.movie"
                    + " left join fetch ms.cinemaHall"
                    + " where ms.movie.id = :id"
                    + " and ms.showTime between :startOfDay and :startOfNextDay",
                            MovieSession.class);
            findAvailableSessionQuery.setParameter("id", movieId);
            findAvailableSessionQuery.setParameter("startOfDay", date.atStartOfDay());
            findAvailableSessionQuery.setParameter("startOfNextDay",
                    date.plusDays(1).atStartOfDay());
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available session "
                    + "by movie id: " + movieId + ", on: " + date, e);
        }
    }
}
