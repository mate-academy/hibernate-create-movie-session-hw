package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        try {
            return HibernateUtil.getSessionFactory().fromTransaction(session -> {
                session.persist(movieSession);
                return movieSession;
            });
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't save movie session to DB: " + movieSession.getId(), e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return getQueryById(session)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie session by id");
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return getQueryOfAvailableSessions(session)
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There are no available sessions for this date " + date.toString());
        }
    }

    private static Query<MovieSession> getQueryById(Session session) {
        return session.createQuery("""
                from MovieSession ms
                left join fetch ms.cinemaHall
                left join fetch ms.movie
                where ms.id = :id
                """, MovieSession.class);
    }

    private static Query<MovieSession> getQueryOfAvailableSessions(Session session) {
        return session.createQuery("""
                from MovieSession ms
                left join fetch ms.cinemaHall
                left join fetch ms.movie
                where ms.movie.id = :movieId and Date(ms.showTime) = :date
                """, MovieSession.class);
    }
}
