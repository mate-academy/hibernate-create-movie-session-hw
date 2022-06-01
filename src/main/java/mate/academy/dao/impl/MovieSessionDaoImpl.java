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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't add in to DB movie session: "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByuId = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id ", MovieSession.class);
            getMovieSessionByuId.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionByuId.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfTheDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfTheDay = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSession = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.showTime between :startOfTheDay and :endOfTheDay "
                    + "and ms.movie.id = :movieId", MovieSession.class);
            getAvailableSession.setParameter("movieId", movieId);
            getAvailableSession.setParameter("startOfTheDay", startOfTheDay);
            getAvailableSession.setParameter("endOfTheDay", endOfTheDay);
            return getAvailableSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie session for id: "
                    + movieId + " at: " + date, e);
        }
    }
}
