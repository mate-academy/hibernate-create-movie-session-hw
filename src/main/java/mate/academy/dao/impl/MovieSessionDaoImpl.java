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
            throw new DataProcessingException("Can not add new MovieSession: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can not get MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime daysBeginning = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime daysEnding = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableSessionsQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "where ms.movie.id = :id AND ms.localDateTime "
                    + "BETWEEN :fromDate AND :toDate", MovieSession.class);
            availableSessionsQuery.setParameter("id", movieId);
            availableSessionsQuery.setParameter("fromDate", daysBeginning);
            availableSessionsQuery.setParameter("toDate", daysEnding);
            return availableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not find available MovieSessions by movieId: "
                    + movieId + " and by LocalDate " + date, e);
        }
    }

}
