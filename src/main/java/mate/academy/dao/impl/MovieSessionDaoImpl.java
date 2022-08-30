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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add MovieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get MovieSession with id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "FROM MovieSession m LEFT JOIN FETCH m.cinemaHall WHERE m.id = :movieId "
                + "AND EXTRACT(YEAR FROM :date) = EXTRACT(YEAR FROM m.showTime)"
                + "AND EXTRACT(MONTH FROM :date) = EXTRACT(MONTH FROM m.showTime)"
                + "AND EXTRACT(DAY FROM :date) = EXTRACT(DAY FROM m.showTime)";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllInTimeMovieSessionQuery =
                    session.createQuery(query, MovieSession.class);
            getAllInTimeMovieSessionQuery.setParameter("movieId", movieId);
            getAllInTimeMovieSessionQuery.setParameter("date", date);
            return getAllInTimeMovieSessionQuery.getResultList();
        }
    }
}
