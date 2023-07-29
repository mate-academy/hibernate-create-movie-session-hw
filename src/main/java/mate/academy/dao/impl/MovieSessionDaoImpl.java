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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add MovieSession to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            Query<MovieSession> allMovieSession = session.createQuery("from MovieSession ms "
                    + " left join fetch ms.cinemaHall "
                    + " left join fetch ms.movie"
                    + " where ms.id = :id", MovieSession.class);
            allMovieSession.setParameter("id", id);
            return allMovieSession.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from MovieSession ms"
                            + "left join fetch ms.movie"
                            + "left join fetch ms.cinemaHall"
                            + "where ms.showTime between :beginSession and :endSession"
                            + "and ms.movie.id = :id")
                    .setParameter("beginSession", date.atStartOfDay())
                    .setParameter("endSession", date.atTime(LocalTime.MAX))
                    .setParameter("id", movieId)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get List of MovieSessions from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
