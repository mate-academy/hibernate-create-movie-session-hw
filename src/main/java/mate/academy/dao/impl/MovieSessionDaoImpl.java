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
        Transaction transaction = null;
        Session session = null;
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
            throw new DataProcessingException("Can't insert MovieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = getSessionFactory().openSession()) {
            Query<MovieSession> allAvailableSessions = session.createQuery(
                    "from MovieSession where showTime between :start_time "
                            + "and :end_time and movie.id = :movie_id",
                    MovieSession.class);
            allAvailableSessions.setParameter("start_time", date.atStartOfDay());
            allAvailableSessions.setParameter("end_time", date.atTime(23, 59,59));
            allAvailableSessions.setParameter("movie_id", movieId);
            return allAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    String.format("Can't get MovieSessions by movie id (%d) and date (%s)",
                            movieId, date.toString()), e);
        }
    }
}
