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
import org.hibernate.HibernateException;
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
            return movieSession;
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall " + movieSession, ex);
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
        } catch (HibernateException ex) {
            throw new DataProcessingException("Can't get a cinemaHall by id: " + id, ex);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionQuery =
                    session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :id and ms.showTime "
                    + "between :startShow and :endShow", MovieSession.class);
            getAllMovieSessionQuery.setParameter("id",movieId);
            getAllMovieSessionQuery.setParameter("startShow",
                    LocalDateTime.of(date, LocalTime.of(0,0)));
            getAllMovieSessionQuery.setParameter("endShow",
                    LocalDateTime.of(date, LocalTime.of(23,59)));
            return getAllMovieSessionQuery.getResultList();
        } catch (HibernateException ex) {
            throw new DataProcessingException("Can't get all available sessions for movieId: "
            + movieId + "by date: " + date, ex);
        }
    }
}
