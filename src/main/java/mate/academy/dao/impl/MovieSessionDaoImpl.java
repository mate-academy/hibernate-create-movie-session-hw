package mate.academy.dao.impl;

import java.time.LocalDateTime;
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
        Session session = null;
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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(
            Long movieId,
            LocalDateTime fromDateTime,
            LocalDateTime toDateTime) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms where ("
                    + "ms.movie.id = :movieIdParam "
                    + "and (ms.showTime between :fromDtParam and :toDtParam))",
                    MovieSession.class);
            findAvailableSessionsQuery.setParameter("movieIdParam", movieId);
            findAvailableSessionsQuery.setParameter("fromDtParam", fromDateTime);
            findAvailableSessionsQuery.setParameter("toDtParam", toDateTime);
            return findAvailableSessionsQuery.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException(
                    "Can't find in DB AvailableSessions with movie_id " + " = " + movieId
                    + " and fromDateTime = " + fromDateTime + " and "
                    + "toDateTime = " + toDateTime, e);
        }
    }
}
