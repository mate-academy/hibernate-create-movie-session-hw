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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't insert MovieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery("from MovieSession m "
                    + "left join fetch m.movie"
                    + " join fetch m.cinemaHall where m.id = :id");
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByParamQuery =
                    session.createQuery("from MovieSession m "
                            + "left join fetch m.movie "
                            + "join fetch m.cinemaHall "
                            + "where m.movie.id = :id "
                            + "and m.showTime > :start and m.showTime < :end", MovieSession.class);
            getMovieSessionByParamQuery.setParameter("id", movieId);
            getMovieSessionByParamQuery.setParameter("start", date.atStartOfDay());
            getMovieSessionByParamQuery.setParameter("end", date.atTime(23, 59, 59));
            return getMovieSessionByParamQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all MovieSessions from DB", e);
        }
    }
}
