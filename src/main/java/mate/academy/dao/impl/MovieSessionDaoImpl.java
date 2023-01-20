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
            throw new DataProcessingException("Can't insert movie session "
                    + movieSession + " to DB", e);
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
            throw new DataProcessingException("Can't get a movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfTheDay = LocalDateTime.of(date, LocalTime.parse("00:00"));
        LocalDateTime endOfTheDay = LocalDateTime.of(date, LocalTime.parse("23:59:59"));
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAvailableSessions = session.createQuery("from MovieSession ms "
                    + "join fetch ms.movie "
                    + "join fetch ms.cinemaHall "
                    + "where  ms.movie.id = :id "
                    + "and ms.showTime between :startOfTheDay and :endOfTheDay");
            getAvailableSessions.setParameter("id", movieId);
            getAvailableSessions.setParameter("startOfTheDay", startOfTheDay);
            getAvailableSessions.setParameter("endOfTheDay", endOfTheDay);
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any sessions from DB for movie with id "
                    + movieId + " on date " + date, e);
        }
    }
}
