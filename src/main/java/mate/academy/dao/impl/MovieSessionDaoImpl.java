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
import org.hibernate.HibernateException;
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session" + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionById = session.createQuery("from MovieSession as ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return getMovieSessionById.uniqueResultOptional();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByDate
                    = session.createQuery("from MovieSession as ms "
                    + "left join fetch ms.movie as m "
                    + "left join fetch ms.cinemaHall as c "
                    + "where m.id = :id "
                    + "and ms.showTime >= :startDate "
                    + "and ms.showTime <= :endDate", MovieSession.class);
            getMovieSessionByDate.setParameter("id", movieId);
            getMovieSessionByDate.setParameter("startDate", date.atStartOfDay());
            getMovieSessionByDate.setParameter("endDate", date.atTime(LocalTime.MAX));
            return getMovieSessionByDate.getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get a movie: "
                    + movieId + " by date: " + date, e);
        }
    }
}
