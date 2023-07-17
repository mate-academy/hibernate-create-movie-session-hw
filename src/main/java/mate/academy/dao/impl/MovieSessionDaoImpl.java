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
    public MovieSession add(MovieSession entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save movie session: " + entity, e);
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
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery =
                    session.createQuery("from MovieSession", MovieSession.class);
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findMovieSessionByRequiredDateQuery = session.createQuery(
                    "from MovieSession ms "
                    + "where ms.movie.id = :id "
                    + "and year(ms.showTime) = :year "
                    + "and month(ms.showTime) = :month "
                    + "and day(ms.showTime) = :day", MovieSession.class);
            findMovieSessionByRequiredDateQuery.setParameter("id", movieId);
            findMovieSessionByRequiredDateQuery.setParameter("year", date.getYear());
            findMovieSessionByRequiredDateQuery.setParameter("month", date.getMonthValue());
            findMovieSessionByRequiredDateQuery.setParameter("day", date.getDayOfMonth());
            return findMovieSessionByRequiredDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by movie id: "
                    + movieId + " and date: " + date.toString(), e);
        }
    }
}
