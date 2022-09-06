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
        Transaction transaction = null;
        Session session = null;
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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessions = session.createQuery(
                    "from MovieSession m "
                            + "where m.sessionTime between :from and :to "
                            + "and m.movie.id = :movieid",
                    MovieSession.class);
            getAvailableSessions.setParameter("from", date.atTime(LocalTime.MIN));
            getAvailableSessions.setParameter("to", date.atTime(LocalTime.MAX));
            getAvailableSessions.setParameter("movieid", movieId);
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't fetch all movieSession from DB", e);
        }
    }
}
