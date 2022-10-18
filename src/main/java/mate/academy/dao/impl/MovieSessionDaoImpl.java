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
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSession.setParameter("id", id);
            return Optional.ofNullable(getMovieSession.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session with id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAllAvailableSessions(Long movieId, LocalDate date) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessions
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie mv "
                    + "left join fetch ms.cinemaHall "
                    + "where mv.id = :id "
                    + "and DATE(ms.showTime) = :date", MovieSession.class);
            getAllMovieSessions.setParameter("id", movieId);
            getAllMovieSessions.setParameter("date", sqlDate);
            return getAllMovieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions", e);
        }
    }
}
