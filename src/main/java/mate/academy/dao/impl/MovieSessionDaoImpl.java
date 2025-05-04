package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession to DB " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession = session.createQuery("from MovieSession ms "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSession.setParameter("id", id);
            return getMovieSession.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessions = session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie m "
                            + "where m.id = :movieId "
                            + "and ms.showTime between :startOfDay and :endOfDay",
                    MovieSession.class);
            getAvailableSessions.setParameter("movieId", movieId);
            getAvailableSessions.setParameter("startOfDay", startOfDay);
            getAvailableSessions.setParameter("endOfDay", endOfDay);
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find all available sessions in DB ", e);
        }
    }
}
