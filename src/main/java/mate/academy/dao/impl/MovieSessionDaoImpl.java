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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, ex);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("FROM MovieSession m "
                            + "WHERE m.movie.id = :movieId "
                            + "AND DATE(m.sessionDateTime) = :date", MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception ex) {
            throw new DataProcessingException(String.format(
                    "Can't get available movie sessions for movie with id: %d and date: %s",
                    movieId, date), ex);
        }
    }
}
