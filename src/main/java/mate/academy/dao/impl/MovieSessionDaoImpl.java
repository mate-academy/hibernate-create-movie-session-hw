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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final SessionFactory factory =
            HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("An error occurred while "
                    + "processing query to add new movie session = " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("An error occurred exception while "
                    + "processing query to get movie session by id = " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery("FROM MovieSession m "
                    + "WHERE m.movie.id = :id AND m.showTime "
                    + "BETWEEN :startDateTime AND :endDateTime", MovieSession.class);
            movieSessionQuery.setParameter("id", movieId);
            movieSessionQuery.setParameter("startDateTime", date.atStartOfDay());
            movieSessionQuery.setParameter("endDateTime", date.atTime(LocalTime.MAX));
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("An error occurred while "
                    + "processing query to get available sessions with input params: "
                    + "movie ID = " + movieId + ", date = " + date, e);
        }
    }
}
