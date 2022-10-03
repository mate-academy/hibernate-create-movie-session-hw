package mate.academy.dao.impl;

import java.time.LocalDate;
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
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Couldn't add movie session to DB: "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie session by id from DB: "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> getAllByMovieIdAndDate(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession "
                            + "where movie_id = :movieId "
                            + "AND showTime BETWEEN :beginOfDay AND :endOfDay",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("beginOfDay", LocalTime.MIN.atDate(localDate));
            query.setParameter("endOfDay", LocalTime.MAX.atDate(localDate));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all movie sessions by movieId from DB: "
                    + movieId + " " + localDate, e);
        }
    }
}
