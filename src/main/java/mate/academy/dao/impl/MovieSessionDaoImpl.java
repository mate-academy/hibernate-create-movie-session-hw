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

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session: " + movieSession, e);
        } finally {
            session.close();
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MovieSession movieSession = session.find(MovieSession.class, id);
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find movie session with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM MovieSession ms "
                                    + "WHERE ms.movie.id = :movieId "
                                    + "AND ms.showTime BETWEEN :startOfDay AND :endOfDay",
                            MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("startOfDay", date.atStartOfDay())
                    .setParameter("endOfDay", date.atTime(23, 59, 59))
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't retrieve the list of available movie sessions",
                    e);
        }
    }
}
