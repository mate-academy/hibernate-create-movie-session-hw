package mate.academy.dao.impl;

import java.time.LocalDate;
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
        Transaction transaction;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't add in DB movie_session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id", MovieSession.class);
            movieSessionQuery.setParameter("id",id);
            return movieSessionQuery.uniqueResultOptional();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get from DB cinema_hall by ID" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionList = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id "
                    + "and ms.showTime >= :timeMin "
                    + "and ms.showTime <= :timeMax", MovieSession.class);
            movieSessionList.setParameter("id",movieId)
                    .setParameter("timeMin", date.atTime(LocalTime.MIN))
                    .setParameter("timeMax",date.atTime(LocalTime.MAX));
            return movieSessionList.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get data from DB", e);
        }
    }
}
