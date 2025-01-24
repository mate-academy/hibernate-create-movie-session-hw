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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session" + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllCinemaHallsQuery = session
                    .createQuery("from MovieSession ms "
                            + "left join fetch ms.movie m "
                            + "left join fetch ms.cinemaHall ch "
                            + "where extract(date from ms.startTime) = :date "
                            + "and m.id = :movieId", MovieSession.class);
            getAllCinemaHallsQuery.setParameter("date", date);
            getAllCinemaHallsQuery.setParameter("movieId", movieId);
            List<MovieSession> resultList = getAllCinemaHallsQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get sessions for movie with id: " + movieId
                    + " for date: " + date, e);
        }
    }
}
