package mate.academy.dao.impl;

import java.time.LocalDate;
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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
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
            Query<MovieSession> sessionQuery = session.createQuery("from MovieSession s "
                    + "left join fetch s.cinemaHall "
                    + "left join fetch s.movie "
                    + "where s.id = :id", MovieSession.class);
            sessionQuery.setParameter("id", id);
            return sessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String queryString = """
                FROM MovieSession s
                LEFT JOIN FETCH s.cinemaHall
                LEFT JOIN FETCH s.movie
                WHERE s.id = :id
                AND s.showTime >= :startOfDay
                AND s.showTime < :endOfDay
                """;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(queryString, MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("startOfDay", date.atStartOfDay());
            query.setParameter("endOfDay", date.atStartOfDay().plusDays(1L));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get movie sessions from the database for date " + date
                            + " and movie with id" + movieId, e);
        }
    }
}
