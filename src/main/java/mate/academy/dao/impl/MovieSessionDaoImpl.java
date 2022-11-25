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
            throw new DataProcessingException("Can't insert new movie session " + movieSession, e);
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
        String hqlQuery = "select ms from MovieSession ms where ms.movie.id = :movieId "
                + "and ms.timeOfBeginning between :midnight and :endOfDay";
        LocalDateTime midnight = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableMovieSessions = session
                        .createQuery(hqlQuery, MovieSession.class);
            getAllAvailableMovieSessions.setParameter("movieId", movieId);
            getAllAvailableMovieSessions.setParameter("midnight", midnight);
            getAllAvailableMovieSessions.setParameter("endOfDay", endOfDay);
            return getAllAvailableMovieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions on " + date, e);
        }
    }

}
