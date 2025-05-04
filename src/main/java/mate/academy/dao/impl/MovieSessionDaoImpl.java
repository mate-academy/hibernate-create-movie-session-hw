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
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl extends AbstractDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add movie-session: " + movieSession + " to Database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get a movie-session by id: " + id + " from Database", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime beginOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM MovieSession AS ms "
                            + " LEFT JOIN FETCH ms.movie LEFT JOIN FETCH ms.cinemaHall "
                            + " WHERE ms.movie.id = :id "
                            + " AND ms.showTime BETWEEN :begin AND :end ", MovieSession.class)
                            .setParameter("id", movieId)
                            .setParameter("begin", beginOfDay)
                            .setParameter("end", endOfDay)
                            .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions from Database", e);
        }
    }
}
