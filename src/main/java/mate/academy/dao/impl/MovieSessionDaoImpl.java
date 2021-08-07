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
            throw new DataProcessingException("Cant add movie session to DB ", e);
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
            throw new DataProcessingException("Cant get movie session with id : " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionAtDateQuery =
                    session.createQuery("from MovieSession ms "
                                    + "where ms.movie.id = :movieId "
                                    + "and ms.showTime between :from and :to",
                    MovieSession.class);
            getMovieSessionAtDateQuery.setParameter("movieId", movieId);
            getMovieSessionAtDateQuery.setParameter("from", date.atTime(0, 0, 0));
            getMovieSessionAtDateQuery.setParameter("to", date.atTime(23,59,59));
            return getMovieSessionAtDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get all movie sessions with"
                    + " movie id : " + movieId
                    + " and date : " + date, e);
        }
    }
}
