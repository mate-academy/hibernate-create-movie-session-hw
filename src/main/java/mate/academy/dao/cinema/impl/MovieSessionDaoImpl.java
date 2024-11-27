package mate.academy.dao.cinema.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.cinema.MovieSessionDao;
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
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(movieSession);
            tx.commit();
            return movieSession;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DataProcessingException("Could not add movie session "
                    + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            MovieSession movieSession = (MovieSession) session.get(MovieSession.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Could not get movie session with ID: "
                    + id, e);
        }
    }

    public List<MovieSession> findAvailableMovieSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MovieSession ms "
                            + "WHERE ms.movie.id = :movieID and "
                            + "DATE (ms.showTime) = :date", MovieSession.class)
                    .setParameter("movieID", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Could not find available sessions for movie ID "
                    + movieId + " on " + date, e);
        }
    }
}
