package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    public static final String ERROR_DURING_CREATING_MOVIE_SESSION =
            "Error during creating movie session -> %s";
    public static final String ERROR_DURING_RETRIEVING_MOVIE_SESSION_WITH_ID =
            "Error during retrieving movie session with id -> %d";
    public static final String SELECT_SESSION_BY_DAY_AND_MOVIE =
            """
                    SELECT ms
                    FROM MovieSession ms
                    WHERE ms.showTime BETWEEN :startDay AND :endDay 
                    AND ms.movie.id = :movieId
                    """;
    public static final String WITH_ID_D_AND_SUCH_DATE =
            "Error during retrieving movie session for movie with id -> %d, and such date -> %s.";

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    ERROR_DURING_CREATING_MOVIE_SESSION.formatted(movieSession), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_MOVIE_SESSION_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<MovieSession> findAllAvailableSessions(Long movieId, LocalDate date) {
        //SELECT * FROM sessions WHERE DAY(sessions.date) = DAY(:date) AND movie.id = :movieId
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            SELECT_SESSION_BY_DAY_AND_MOVIE, MovieSession.class
                    ).setParameter("startDay", date.atStartOfDay())
                    .setParameter("endDay", date.atTime(LocalTime.MAX))
                    .setParameter("movieId", movieId).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    WITH_ID_D_AND_SUCH_DATE
                            .formatted(movieId, date), e);
        }
    }
}
