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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            Query<MovieSession> getAvailableMovieSessionQuery = session
                    .createQuery("FROM MovieSession m "
                                    + "WHERE m.showTime between :dateFrom AND :dateTo "
                                    + "AND m.movie.id = :id",
                            MovieSession.class);
            getAvailableMovieSessionQuery.setParameter("dateFrom", date.atStartOfDay());
            getAvailableMovieSessionQuery.setParameter("dateTo", date.atTime(LocalTime.MAX));
            getAvailableMovieSessionQuery.setParameter("id", movieId);
            return getAvailableMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions from DB "
                    + "with movie id " + movieId + "and date " + date, e);
        }
    }
}
