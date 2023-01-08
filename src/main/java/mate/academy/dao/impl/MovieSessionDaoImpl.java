package mate.academy.dao.impl;

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
            Query<MovieSession> getMovieSessionById =
                    session.createQuery("from MovieSession ms"
                    + " left join fetch ms.movie"
                    + " left join fetch ms.cinemaHall"
                    + " where ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionById.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date) {
        LocalDateTime localDateTimeMin = LocalDateTime.of(date.toLocalDate(), LocalTime.MIN);
        LocalDateTime localDateTimeMax = LocalDateTime.of(date.toLocalDate(), LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessions =
                    session.createQuery("from MovieSession ms"
                            + " left join fetch ms.movie"
                            + " left join fetch ms.cinemaHall"
                            + " where ms.showTime between :localDateTimeMin and :localDateTimeMax");
            getAllAvailableSessions.setParameter("localDateTimeMin", localDateTimeMin);
            getAllAvailableSessions.setParameter("localDateTimeMax", localDateTimeMax);
            return getAllAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions", e);
        }
    }
}
