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
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Error adding new movieSession", e);
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
            throw new DataProcessingException("Error getting movie session id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            Query<MovieSession> query = session.createQuery("from MovieSession ms"
                            + " left join fetch ms.movie"
                            + " where ms.movie.id = :id "
                            + " and ms.showTime between :startOfDay and :endOfDay",
                    MovieSession.class);
            query.setParameter("id", movieId)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error finding available sessions for movie id: "
                    + movieId + " on date: " + date, e);
        }
    }
}
