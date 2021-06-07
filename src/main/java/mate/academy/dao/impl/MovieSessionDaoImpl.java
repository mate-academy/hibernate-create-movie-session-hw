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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieQuery = session.createQuery("from MovieSession m "
                            + "left join fetch m.movie "
                            + "left join fetch m.cinemaHall "
                            + "where m.id = :id", MovieSession.class);
            getMovieQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery = session
                    .createQuery("from MovieSession m "
                            + "left join fetch m.movie "
                            + "left join fetch m.cinemaHall "
                            + "where m.movie.id = :movieId AND m.showTime "
                            + "between :startDay and :finishDay", MovieSession.class);
            LocalDateTime startDay = date.atTime(LocalTime.MIN);
            LocalDateTime finishDate = date.atTime(LocalTime.MAX);
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            findAvailableSessionsQuery.setParameter("startDay", startDay);
            findAvailableSessionsQuery.setParameter("finishDay", finishDate);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movieSession ", e);
        }
    }
}
