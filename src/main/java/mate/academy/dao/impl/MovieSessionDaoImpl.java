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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movies session "
                    + movieSession + " to Db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery("FROM MovieSession ms "
                      + "left join fetch ms.movie left join fetch ms.cinemaHall "
                      + "where ms.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id "
                    + id + " from Db", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.atTime(LocalTime.MAX);
            Query<MovieSession> findAvailableSessionsQuery
                    = session.createQuery("FROM MovieSession ms"
                    + " left join fetch ms.movie m left join fetch ms.cinemaHall where m.id = :id"
                    + " and ms.showTime between :startDate and :endDate", MovieSession.class);
            findAvailableSessionsQuery.setParameter("id", movieId);
            findAvailableSessionsQuery.setParameter("startDate", startDate);
            findAvailableSessionsQuery.setParameter("endDate", endDate);
            List<MovieSession> resultList = findAvailableSessionsQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DataProcessingException("No available movie sessions by movie id "
            + movieId + " and current date " + date + " from DB");
        }
    }
}
