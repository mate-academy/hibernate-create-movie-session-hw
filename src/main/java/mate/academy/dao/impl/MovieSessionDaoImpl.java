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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<MovieSession> query = session.createQuery("from MovieSession o "
                    + "left join fetch o.cinemaHall, o.movie where o.id = :id", MovieSession.class);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all movie sessions from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<MovieSession> query = session.createQuery("SELECT ms "
                    + "FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.cinemaHall c "
                    + "LEFT JOIN FETCH ms.movie m "
                    + "WHERE m.id = :movieId "
                    + "AND YEAR(ms.showTime) = YEAR(:targetDate) "
                    + "AND MONTH(ms.showTime) = MONTH(:targetDate) AND DAY(ms.showTime)"
                    + " = DAY(:targetDate)");
            query.setParameter("movieId", movieId);
            query.setParameter("targetDate", date);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get all movie sessions from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
