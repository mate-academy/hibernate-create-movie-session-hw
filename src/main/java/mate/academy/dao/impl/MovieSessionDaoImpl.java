package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieDao;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Inject
    private MovieDao movieDao;

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
            Query<MovieSession> getMovieSessionQuery = session.createQuery("FROM MovieSession s "
                    + "JOIN FETCH s.movie "
                    + "JOIN FETCH s.cinemaHall "
                    + "WHERE s.id = :id");
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);
            Query<MovieSession> findAvailableSessionsQuery = session.createQuery(
                    "SELECT s "
                            + "FROM MovieSession s "
                            + "JOIN FETCH s.movie m "
                            + "JOIN FETCH s.cinemaHall "
                            + "WHERE s.showTime BETWEEN :startOfDay AND :endOfDay "
                            + "AND movie = :movieId",
                    MovieSession.class);
            findAvailableSessionsQuery.setParameter("startOfDay", startOfDay);
            findAvailableSessionsQuery.setParameter("endOfDay", endOfDay);
            findAvailableSessionsQuery.setParameter("movieId", movieDao.get(movieId).get());
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie sessions for movie id: "
                    + movieId + " on date: " + date, e);
        }
    }
}
