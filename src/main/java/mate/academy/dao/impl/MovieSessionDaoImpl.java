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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionById =
                    session.createQuery("from MovieSession s "
                            + "join fetch s.movie "
                            + "join fetch s.cinemaHall "
                            + "where s.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionById.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsQuery =
                    session.createQuery("from MovieSession s "
                            + "join fetch s.movie "
                            + "join fetch s.cinemaHall "
                            + "where s.showTime between :beginDate and :endDate "
                            + "and s.movie.id = :movieId", MovieSession.class);
            getAllMovieSessionsQuery.setParameter("beginDate", date.atTime(LocalTime.MIN));
            getAllMovieSessionsQuery.setParameter("endDate", date.atTime(LocalTime.MAX));
            getAllMovieSessionsQuery.setParameter("movieId", movieId);
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions from db:"
                    + " movie id=" + movieId + ", date=" + date.toString(), e);
        }
    }
}
