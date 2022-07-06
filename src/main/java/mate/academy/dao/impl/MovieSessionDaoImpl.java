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
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Cannot save to DB this movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        String hql = "FROM MovieSession ms "
                + "LEFT JOIN FETCH ms.movie "
                + "LEFT JOIN FETCH ms.cinemaHall "
                + "WHERE ms.id = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(hql, MovieSession.class);
            query.setParameter("id", id);
            MovieSession movieSession = query.getSingleResult();
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String hql = "FROM MovieSession ms "
                + "LEFT JOIN FETCH ms.movie m "
                + "LEFT JOIN FETCH ms.cinemaHall "
                + "WHERE m.id = :movieId "
                + "AND ms.showTime BETWEEN :lowerBound AND :upperBound";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(hql, MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("lowerBound", date.atStartOfDay());
            query.setParameter("upperBound", date.plusDays(1).atStartOfDay());
            List<MovieSession> list = query.getResultList();
            return list;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions by movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
