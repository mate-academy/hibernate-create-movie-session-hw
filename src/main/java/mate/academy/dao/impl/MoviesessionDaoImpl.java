package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MoviesessionDaoImpl implements MovieSessionDao {
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
            throw new DataProcessingException("Cannot persist movieSession" + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get movieSession with id" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from MovieSession ms "
                                    + "join fetch ms.movie "
                                    + "join fetch ms.cinemaHall "
                                    + "where ms.movie.id = :movieId "
                                    + "and ms.startTime between :start and :end",
                            MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("start", date.atStartOfDay())
                    .setParameter("end", date.atTime(23, 59, 59))
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get available sessions for movieId "
                    + movieId, e);
        }
    }
}
