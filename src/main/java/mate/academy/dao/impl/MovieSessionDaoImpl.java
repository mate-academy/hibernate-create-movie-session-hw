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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
            Query<MovieSession> getAllMovieSessionQuery
                    = session.createQuery("from MovieSession ms "
                    + "where ms.id = :id", MovieSession.class);
            getAllMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getAllMovieSessionQuery.uniqueResult());
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByIdAndDateQuery
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "where m.id = :movieId "
                    + "and ms.showTime between :startOfDay and :endOfDay", MovieSession.class);
            getMovieSessionByIdAndDateQuery.setParameter("movieId", movieId);
            getMovieSessionByIdAndDateQuery.setParameter("startOfDay",
                    LocalDateTime.of(date, LocalTime.of(0, 0, 0)));
            getMovieSessionByIdAndDateQuery.setParameter("endOfDay",
                    LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
            return getMovieSessionByIdAndDateQuery.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't find available movie session with "
                    + " movie id " + movieId + " and date " + date, e);
        }
    }
}
