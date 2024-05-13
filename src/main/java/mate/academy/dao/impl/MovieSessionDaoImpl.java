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
            throw new DataProcessingException("Can't insert cinemaHall " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = date.atTime(23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvaliableSessionsQuery = session
                    .createQuery("from MovieSession ms "
                                    + "left join fetch ms.movie "
                                    + "left join fetch ms.cinemaHall "
                                    + "where movie.id = :movieId and "
                                    + "ms.localDateTime between :startDay and :endDay",
                            MovieSession.class);
            findAvaliableSessionsQuery.setParameter("movieId", movieId);
            findAvaliableSessionsQuery.setParameter("startDay", startDay);
            findAvaliableSessionsQuery.setParameter("endDay", endDay);
            return findAvaliableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions for "
                    + "movieId = " + movieId
                    + " and date = " + date, e);
        }
    }
}
