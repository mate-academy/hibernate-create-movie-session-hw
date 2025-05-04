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
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession" + movieSession
                    + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionById
                    = session.createQuery("FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.movie "
                    + "LEFT JOIN FETCH ms.cinemaHall "
                    + "WHERE ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionById.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAllSessions = session.createQuery(
                    "from MovieSession m "
                    + "left join fetch m.cinemaHall "
                    + "left join fetch m.movie"
                    + " where m.movie.id = :movie_id and m.time "
                    + "between :start and :end", MovieSession.class);
            findAllSessions.setParameter("movie_id", movieId);
            findAllSessions.setParameter("startDate", date.atStartOfDay());
            findAllSessions.setParameter("endDate", date.atTime(LocalTime.MAX));
            return findAllSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movies sessions "
                    + movieId + " or date " + date, e);
        }
    }
}
