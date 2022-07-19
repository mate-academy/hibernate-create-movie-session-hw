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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session to db,"
                    + " MovieSession: " + movieSession, e);
        }
        return null;
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
    public List<MovieSession> findAvailableSessions(Long moveId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(
                    "FROM MovieSession m "
                            + "LEFT JOIN FETCH m.movie "
                            + "WHERE m.showTime > :startOfDay AND m.showTime < :endOfDay "
                            + "AND m.movie.id = :movieId",
                    MovieSession.class);
            LocalDateTime atStartOfDay = date.atStartOfDay();
            LocalDateTime atEndOfDay = date.atTime(23, 59, 59);
            query.setParameter("startOfDay", atStartOfDay);
            query.setParameter("endOfDay", atEndOfDay);
            query.setParameter("movieId", moveId);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find session by movie id: "
                    + moveId + " and date: " + date, e);
        }
    }
}
