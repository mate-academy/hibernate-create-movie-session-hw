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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session "
                    + movieSession + " to the database", e);
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
            throw new DataProcessingException("Can't get movie session by id "
                    + id + " from the database", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> fromMovieSession =
                    session.createQuery("from MovieSession m where m.movie.id = :id and m.showTime "
                            + "between :startOfTheDay and :endOfTheDay", MovieSession.class);
            fromMovieSession.setParameter("id", movieId);
            fromMovieSession.setParameter("startOfTheDay", date.atStartOfDay());
            fromMovieSession.setParameter("endOfTheDay", date.atTime(LocalTime.MAX));
            return fromMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions for movie by id "
                    + movieId + " at " + date + " from the database", e);
        }
    }
}
