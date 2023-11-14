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
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataProcessingException(String.format("Can't add "
                    + "movie session: %s to DB", movieSession), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get "
                    + "movie session with id: " + id, e);
        }
    }

    //join fetch
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> getAllAvailableSessions = session.createQuery(
                    "from MovieSession session "
                            + "join fetch session.movie "
                            + "where session.movie.id = :id "
                            + "and DATE(session.showTime) = :date",
                    MovieSession.class);
            getAllAvailableSessions.setParameter("date", date);
            getAllAvailableSessions.setParameter("id", movieId);

            return getAllAvailableSessions.getResultList();
        }
    }
}
