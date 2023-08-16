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
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final String ID = "id";
    private static final String START_OF_DAY = "startOfDay";
    private static final String END_OF_DAY = "endOfDay";
    private static final int NEXT_DAY = 1;
    private static final String GET_AVAILABLE_QUERY = """
            FROM MovieSession s
            WHERE s.id = :id
            AND s.showTime >= :startOfDay
            AND s.showTime < :endOfDay
            """;

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
            throw new DataProcessingException("Can't insert a new movie session "
                    + movieSession, e);
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
            throw new DataProcessingException("Can't get a movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableQuery = session.createQuery(
                    GET_AVAILABLE_QUERY,
                    MovieSession.class);
            getAvailableQuery.setParameter(ID, movieId);
            getAvailableQuery.setParameter(START_OF_DAY, date.atStartOfDay());
            getAvailableQuery.setParameter(END_OF_DAY, date.atStartOfDay().plusDays(NEXT_DAY));
            return getAvailableQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions from the database", e);
        }
    }
}
