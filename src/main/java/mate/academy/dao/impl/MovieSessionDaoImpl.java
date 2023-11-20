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
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movieSession "
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
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.MAX);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionQuery = session.createQuery(
                    "from MovieSession "
                            + "where movie.id = :id AND "
                            + "showTime >= :startDayTime AND "
                            + "showTime <= :endDayTime",
                    MovieSession.class);
            findAvailableSessionQuery.setParameter("id", movieId);
            findAvailableSessionQuery.setParameter("startDayTime", startDateTime);
            findAvailableSessionQuery.setParameter("endDayTime", endDateTime);
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession list by movie id: "
                    + movieId + " on chosen date: " + date, e);
        }
    }

}
