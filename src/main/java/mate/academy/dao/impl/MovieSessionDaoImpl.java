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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery("From MovieSession as ms "
                    + "left join fetch ms.movie as m "
                    + "left join fetch ms.cinemaHall where ms.id = :id", MovieSession.class);
            return movieSessionQuery.setParameter("id", id).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession = session.createQuery("From MovieSession m "
                    + "where m.id = :id and m.showTime "
                    + "between :startTime and :endTime", MovieSession.class);
            LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
            LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.MAX);
            getMovieSession.setParameter("id", movieId);
            getMovieSession.setParameter("startTime", startDateTime);
            getMovieSession.setParameter("endTime", endDateTime);
            return getMovieSession.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all movie sessions from DB by id: "
                    + movieId + " and in specific time: " + date);
        }
    }
}
