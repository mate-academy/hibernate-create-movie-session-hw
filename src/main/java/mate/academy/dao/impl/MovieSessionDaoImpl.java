package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private static final String FORMATTER = "dd.MM.yyyy";

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
            throw new DataProcessingException("Can't insert movie session: "
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
            throw new DataProcessingException("Can't get a movie session by id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "FROM MovieSession ms "
                    + "LEFT JOIN FETCH ms.movie m "
                    + "LEFT JOIN FETCH ms.cinemaHall ch "
                    + "WHERE ms.showTime BETWEEN :startTime AND :endTime AND m.id = :movieId";
            Query<MovieSession> findAvailableSessionsQuery =
                    session.createQuery(query, MovieSession.class);
            findAvailableSessionsQuery.setParameter("startTime", date.atTime(LocalTime.MIN));
            findAvailableSessionsQuery.setParameter("endTime", date.atTime(LocalTime.MAX));
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            return findAvailableSessionsQuery.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions for movie with id: "
                    + movieId + " and date: " + date.format(DateTimeFormatter.ofPattern(FORMATTER))
                    + " from DB", e);
        }
    }
}
