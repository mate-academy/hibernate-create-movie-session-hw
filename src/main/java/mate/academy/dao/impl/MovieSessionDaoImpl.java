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
    private static final LocalTime TIME_FROM = LocalTime.of(00, 00, 00);
    private static final LocalTime TIME_TO = LocalTime.of(23, 59, 59);

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
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
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery(
                    "from MovieSession ms "
                            + " left join fetch ms.cinemaHall"
                            + " left join fetch ms.movie"
                            + " where ms.id = :id", MovieSession.class);
            return query.setParameter("id", id).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime beginningOfTheDay = LocalDateTime.of(date, TIME_FROM);
            LocalDateTime endOfTheDay = LocalDateTime.of(date, TIME_TO);
            Query<MovieSession> findAllMovieSessionsQuery
                    = session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.showTime between :beginningTime AND :endTime "
                            + "AND ms.movie.id = :movieId "
                            + "ORDER BY ms.showTime ASC ",
                    MovieSession.class);
            findAllMovieSessionsQuery.setParameter("movieId", movieId);
            findAllMovieSessionsQuery.setParameter("beginningTime", beginningOfTheDay);
            findAllMovieSessionsQuery.setParameter("endTime", endOfTheDay);
            return findAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions by movie id "
                    + movieId + " and data: " + date, e);
        }
    }
}
