package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private SessionFactory sessionFactory;

    public MovieSessionDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public MovieSession add(MovieSession entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot save MovieSession entity "
            + entity + " in DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Optional<MovieSession> get(Long movieSessionId) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie "
                            + "LEFT JOIN FETCH  ms.cinemaHall "
                            + "WHERE ms.id = :id", MovieSession.class);
            movieSessionQuery.setParameter("id", movieSessionId);
            return movieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get MovieSession entity by ID="
                    + movieSessionId + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay().minusSeconds(1);
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> allMovieSessionQuery = session
                    .createQuery("FROM MovieSession ms "
                            + "WHERE ms.movie.id = :movie_id "
                            + "AND ms.showTime BETWEEN :date_from AND :date_to",
                            MovieSession.class);
            allMovieSessionQuery.setParameter("movie_id", movieId);
            allMovieSessionQuery.setParameter("date_from", from);
            allMovieSessionQuery.setParameter("date_to", to);
            return allMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get list of MovieSessions from DB", e);
        }
    }
}
