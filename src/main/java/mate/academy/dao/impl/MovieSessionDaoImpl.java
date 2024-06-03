package mate.academy.dao.impl;

import static mate.academy.util.HqlQueries.GET_ALL_MOVIE_SESSIONS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import org.hibernate.SessionFactory;

@Dao
public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {

    public MovieSessionDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        return executeInsideTransaction(session -> {
            session.persist(movieSession);
            return movieSession;
        });
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (var session = factory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get a movie session by id:" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (var session = factory.openSession()) {
            var startOfDay = date.atStartOfDay();
            var endOfDay = date.atTime(LocalTime.MAX);
            return session.createQuery(GET_ALL_MOVIE_SESSIONS, MovieSession.class)
                    .setParameter("moveId", movieId)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay)
                    .list();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't find available sessions for movie id: " + movieId
                            + " on date: " + date, e);
        }
    }
}
