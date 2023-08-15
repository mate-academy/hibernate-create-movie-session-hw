package mate.academy.dao.impl;

import java.time.LocalDate;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session to DB. Session: "
                    + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = "from MovieSession m left join fetch m.movie mov where mov.id = :id and"
                + " m.showTime between :dayStart and :dayOver";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMoviesInTimeQuery =
                    session.createQuery(query, MovieSession.class);
            getAllMoviesInTimeQuery.setParameter("id", movieId);
            getAllMoviesInTimeQuery.setParameter("dayStart", date.atStartOfDay());
            getAllMoviesInTimeQuery.setParameter("dayOver", date.atTime(LocalTime.MAX));
            return getAllMoviesInTimeQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movies", e);
        }
    }
}
