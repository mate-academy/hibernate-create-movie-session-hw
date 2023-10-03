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

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

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
            throw new DataProcessingException(
                    "Can't save movie session to DB: " + movieSession.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        String query = """
                from MovieSession ms
                join fetch ms.cinemaHall
                join fetch ms.movie
                where ms.id = :id
                """;
        try (Session session = factory.openSession()) {
            return session.createQuery(query, MovieSession.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie session by id");
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String query = """
                from MovieSession ms
                join fetch ms.cinemaHall
                join fetch ms.movie
                where ms.movie.id = :movieId and Date(ms.showTime) = :date
                """;
        try (Session session = factory.openSession()) {
            return session.createQuery(query, MovieSession.class)
                    .setParameter("movieId", movieId)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "There are no available sessions for this date " + date.toString());
        }
    }
}
