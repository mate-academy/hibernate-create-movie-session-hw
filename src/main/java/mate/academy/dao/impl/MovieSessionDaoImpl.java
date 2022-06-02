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
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session in DB" + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime beginningOfADay = date.atStartOfDay();
        LocalDateTime endOfADay = date.atStartOfDay().plusDays(1).minusSeconds(1);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery = session
                    .createQuery("from MovieSession ms "
                            + "left join fetch ms.cinemaHall "
                            + "left join fetch ms.movie "
                    + "where ms.showTime between :startTime and :endTime "
                    + "and ms.movie.id = :movieId", MovieSession.class);
            getAllAvailableSessionsQuery.setParameter("startTime", beginningOfADay);
            getAllAvailableSessionsQuery.setParameter("endTime", endOfADay);
            getAllAvailableSessionsQuery.setParameter("movieId", movieId);
            return getAllAvailableSessionsQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to get any movie session for movie with id "
                    + movieId + " on " + date + " from DB", e);
        }
    }
}
