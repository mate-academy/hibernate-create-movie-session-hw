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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert MovieSession. " + movieSession, e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        final LocalDateTime startOfDayDay = date.atStartOfDay();
        final LocalDateTime endOfDay = date.atTime(23,59,59);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionsquery = session.createQuery("from MovieSession m "
                    + "WHERE m.movie.id = :id "
                    + "AND m.showTime BETWEEN :start AND :end", MovieSession.class);
            movieSessionsquery.setParameter("id", movieId)
                    .setParameter("start", startOfDayDay)
                    .setParameter("end", endOfDay);
            return movieSessionsquery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all movie sessions by id "
                    + movieId + " for this date: " + date, e);
        }
    }
}
