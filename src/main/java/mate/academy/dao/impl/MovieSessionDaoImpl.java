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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session: "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        String query = "FROM MovieSession ms "
                + "LEFT JOIN FETCH ms.movie "
                + "LEFT JOIN FETCH ms.cinemaHall "
                + "WHERE ms.id = :id";
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getMovieSessionByIdQuery = session
                    .createQuery(query, MovieSession.class);
            getMovieSessionByIdQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionByIdQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "FROM MovieSession ms "
                + "LEFT JOIN FETCH ms.movie m "
                + "LEFT JOIN FETCH ms.cinemaHall "
                + "WHERE m.id = :id AND ms.showTime BETWEEN :beginTime AND :endTime";
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAvailableSessionsQuery = session
                    .createQuery(query, MovieSession.class);
            getAvailableSessionsQuery.setParameter("id", movieId);
            getAvailableSessionsQuery.setParameter("beginTime",
                    LocalDateTime.of(date, LocalTime.MIN));
            getAvailableSessionsQuery.setParameter("endTime",
                    LocalDateTime.of(date, LocalTime.MAX));
            return getAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie sessions by id: "
                    + movieId + " on the date: " + date, e);
        }
    }
}
