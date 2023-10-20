package mate.academy.dao.impl;

import jakarta.persistence.Query;
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
            throw new DataProcessingException("Can't insert movie " + movieSession, e);
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
    public List<MovieSession> findAll(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from MovieSession ms "
                    + "where ms.movie.id = :movieId "
                    + "and ms.showTime BETWEEN :startTime AND :endTime";
            Query getAllMovieSessionsByMovieAndDate = session.createQuery(hql, MovieSession.class);
            getAllMovieSessionsByMovieAndDate.setParameter("movieId", movieId);
            getAllMovieSessionsByMovieAndDate.setParameter("startTime", date.atStartOfDay());
            getAllMovieSessionsByMovieAndDate.setParameter("endTime", date.atTime(23,59,59));
            return getAllMovieSessionsByMovieAndDate.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all movies from DB", e);
        }
    }
}
