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
import org.hibernate.SessionFactory;
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getOrderQuery = session.createQuery("from MovieSession ms"
                    + " left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall"
                    + " where ms.id = :id", MovieSession.class);
            getOrderQuery.setParameter("id", id);
            return getOrderQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getOrderQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall where ms.movie.id = :movieId "
                    + "and year(ms.showTime) = :year "
                    + "and month(ms.showTime) = :month "
                    + "and day(ms.showTime) = :day", MovieSession.class);
            getOrderQuery.setParameter("movieId", movieId);
            getOrderQuery.setParameter("year", date.getYear());
            getOrderQuery.setParameter("month", date.getMonth().getValue());
            getOrderQuery.setParameter("day", date.getDayOfMonth());
            return getOrderQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie: "
                    + movieId + " by date: " + date, e);
        }
    }
}
