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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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
            throw new DataProcessingException("Couldn't add movie session to DB."
                    + movieSession, e);
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
            MovieSession movieSession = session.get(MovieSession.class, id);
            return Optional.ofNullable(movieSession);
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie session from DB by id: "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAllAvailableSessions = session.createQuery(
                    "from MovieSession ms "
                            + "join fetch ms.movie m "
                            + "join fetch ms.cinemaHall ch "
                            + "where m.id = :id "
                            + "and ms.showTime between :startOfDay and :endOfDay",
                    MovieSession.class);
            getAllAvailableSessions.setParameter("id", movieId);
            getAllAvailableSessions.setParameter("startOfDay", date.atStartOfDay());
            getAllAvailableSessions.setParameter("endOfDay", date.atTime(23, 59, 59));
            return getAllAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all available sessions from DB by id: "
                    + movieId + " and show date: " + date, e);
        }
    }
}
