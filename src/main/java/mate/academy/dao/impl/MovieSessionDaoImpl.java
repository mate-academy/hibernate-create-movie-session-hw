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
    private static final String QUERY_FIND_AVAILABLE_SESSION = "from MovieSession ms "
            + "inner join fetch ms.movie m "
            + "where m.id = :movieId "
            + "and (ms.showTime between  :timeFrom and :timeTo)"
            + "order by ms.showTime";

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
            throw new DataProcessingException("Can't add a movieSession: " + movieSession, e);
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
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {

        Session session = null;
        Transaction transaction = null;
        LocalDateTime timeFrom = date.atStartOfDay();
        LocalDateTime timeTo = date.atTime(LocalTime.MAX);

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Query<MovieSession> query
                    = session.createQuery(QUERY_FIND_AVAILABLE_SESSION, MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("timeFrom", timeFrom);
            query.setParameter("timeTo", timeTo);

            transaction.commit();

            return query.getResultList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't find available movieSessions for movie : "
                    + movieId, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
