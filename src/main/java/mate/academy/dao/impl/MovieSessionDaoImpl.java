package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessions = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.cinemaHall "
                    + "left join fetch ms.movie "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSessions.setParameter("id", id);
            return getMovieSessions.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessions = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.cinemaHall "
                    + "left join fetch ms.movie "
                    + "where ms.time >= :time1 and ms.time < :time2", MovieSession.class);
            getAvailableSessions.setParameter("time1",
                    LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                            00,00,00));
            getAvailableSessions.setParameter("time2",
                    LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth() + 1,
                            00,00,00));
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of available movie sessions", e);
        }
    }
}
