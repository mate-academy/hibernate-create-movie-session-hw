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
            throw new DataProcessingException("Can't insert movie session "
                    + movieSession, e);
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
        String queryForFindAvailableSessionsQuery = "FROM MovieSession ms WHERE ms.movie.id "
                + "= :movieId AND ms.showTime >= "
                + ":startOfDay AND ms.showTime <= :endOfDay";
        LocalDateTime startOfDay
                = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                0, 0, 0);
        LocalDateTime endOfDay
                = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery
                    = session.createQuery(queryForFindAvailableSessionsQuery, MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get movies sessions by id: "
                    + movieId + ", and by data: " + date, e);
        }
    }
}
