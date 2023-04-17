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
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add movie session to db: " + movieSession, e);
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
            throw new DataProcessingException("Can`t get movie session from db by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllFromMovieSessionQuery
                    = session.createQuery("from MovieSession ms where ms.movie.id = :id "
                    + "and year(ms.showTime) = :year "
                    + "and month(ms.showTime) = :month "
                    + "and day(ms.showTime) = :day", MovieSession.class);
            getAllFromMovieSessionQuery.setParameter("id", movieId);
            getAllFromMovieSessionQuery.setParameter("year", date.getYear());
            getAllFromMovieSessionQuery.setParameter("month", date.getMonthValue());
            getAllFromMovieSessionQuery.setParameter("day", date.getDayOfMonth());
            return getAllFromMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get movie session by movie id " + movieId
                    + " on date " + date, e);
        }
    }
}
