package mate.academy.dao.impl;

import static mate.academy.util.HibernateUtil.getSessionFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
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
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
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
        try (Session session = getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery
                    = session.createQuery("from MovieSession", MovieSession.class);
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movieSessions from DB", e);
        }
    }

    @Override
    public List<MovieSession> getByMovieIdAndDate(Long movieId, LocalDate date) {
        try (Session session = getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery
                    = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :movieId "
                    + "and ms.showTime between :startOfDate and :endOfDate ", MovieSession.class);
            getAllMovieSessionsQuery.setParameter("movieId", movieId);
            getAllMovieSessionsQuery.setParameter("startOfDate", date.atStartOfDay());
            getAllMovieSessionsQuery.setParameter("endOfDate", date.atTime(23,59,59));
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movieSessions by movieId = " + movieId
                    + " and date = " + date + " from DB", e);
        }
    }
}
