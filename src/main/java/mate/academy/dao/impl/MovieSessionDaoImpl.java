package mate.academy.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(movieSession);
            entityTransaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't add movie session " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            TypedQuery<MovieSession> movieSessionTypedQuery = entityManager
                    .createQuery("SELECT ms "
                                    + "FROM MovieSession ms "
                                    + "LEFT JOIN FETCH ms.movie "
                                    + "LEFT JOIN FETCH ms.cinemaHall "
                                    + "WHERE ms.id = :id",
                            MovieSession.class)
                    .setParameter("id", id);
            entityTransaction.commit();
            return movieSessionTypedQuery
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't find movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            LocalDateTime from = date.atStartOfDay();
            LocalDateTime to = date.atTime(LocalTime.MAX);
            TypedQuery<MovieSession> movieSessions = entityManager
                    .createQuery("SELECT ms "
                            + "FROM MovieSession ms "
                            + "LEFT JOIN FETCH ms.movie "
                            + "LEFT JOIN FETCH ms.cinemaHall "
                            + "WHERE ms.movie.id = :movieId "
                            + "AND ms.showTime BETWEEN :from AND :to",
                            MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("from", from)
                    .setParameter("to", to);
            entityTransaction.commit();
            return movieSessions.getResultList();
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't get available session with params: movieId = "
                    + movieId + ", date = " + date, e);
        }
    }
}
