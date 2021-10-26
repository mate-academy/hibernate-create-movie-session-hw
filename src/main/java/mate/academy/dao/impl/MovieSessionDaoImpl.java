package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert Movie Session " + movieSession, e);
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
            throw new DataProcessingException("Can't get a Movie Session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = factory.openSession()) {
            Query<MovieSession> query = session
                    .createQuery("FROM MovieSession ", MovieSession.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find all Movie Sessions ", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> query =
                    session.createQuery("from MovieSession m where m.movie.id = :id and"
                    + " m.showtime between :startDate and :endDate", MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("startDate",date.atStartOfDay());
            query.setParameter("endDate", date.atTime(23, 59, 59));
            return query.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available"
                   + " movie sessions by date: " + date, e);
        }
    }
}
