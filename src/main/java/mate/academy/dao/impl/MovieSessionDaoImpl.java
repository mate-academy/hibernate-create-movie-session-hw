package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session
                    .createQuery("from MovieSession ms "
                            + "left join fetch ms.movie m "
                            + "left join fetch ms.cinemaHall ch "
                            + "where ms.id =:id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23,59,59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionQuery = session
                    .createQuery("from MovieSession ms "
                    + "join fetch ms.movie "
                    + "join fetch ms.cinemaHall "
                    + "where ms.movie.id =:movieId "
                    + "and ms.showTime between :startOfDay and :endOFDay", MovieSession.class);
            findAvailableSessionQuery.setParameter("movieId", movieId);
            findAvailableSessionQuery.setParameter("startOfDay", startOfDay);
            findAvailableSessionQuery.setParameter("endOFDay", endOfDay);
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available session by movie id = "
                    + movieId + " and date = " + date, e);
        }
    }
}
