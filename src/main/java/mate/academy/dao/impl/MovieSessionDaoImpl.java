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
    private static final String MOVIE_ID = "movieId";
    private static final String START_OF_DAY = "startOfDay";
    private static final String END_OF_DAY = "endOfDay";
    private static final String ID = "id";

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionById = session.createQuery("FROM MovieSession ms "
                    + "JOIN FETCH ms.movie m "
                    + "JOIN FETCH ms.cinemaHall ch "
                    + "WHERE ms.id = :id", MovieSession.class);
            getMovieSessionById.setParameter(ID, id);
            return getMovieSessionById.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableMovieSession =
                    session.createQuery("FROM MovieSession m "
                                    + "LEFT JOIN FETCH m.movie "
                                    + "LEFT JOIN FETCH m.cinemaHall "
                                    + "WHERE m.movie.id = :movieId "
                                    + "AND m.showTime BETWEEN :startOfDay AND :endOfDay ",
                            MovieSession.class);
            findAvailableMovieSession.setParameter(MOVIE_ID, movieId);
            findAvailableMovieSession.setParameter(START_OF_DAY,
                    LocalDateTime.of(localDate, LocalTime.MIN));
            findAvailableMovieSession.setParameter(END_OF_DAY,
                    LocalDateTime.of(localDate, LocalTime.MAX));
            return findAvailableMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available session at: " + localDate
                    + "by movie id" + movieId, e);
        }
    }
}
