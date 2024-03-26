package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.dto.MovieSessionsByDate;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot save session to db: " + movieSession, e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (RuntimeException e) {
            throw new DataProcessingException("Cannot get session from db with id = " + id, e);
        }
    }

    @Override
    public List<MovieSessionsByDate> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSessionsByDate> movies = session.createQuery(
                    "select new mate.academy.dto.MovieSessionsByDate(ms) "
                            + "from MovieSession ms "
                            + "left join ms.movie m "
                            + "where m.id = :id and date(ms.showTime) = :date",
                    MovieSessionsByDate.class);
            movies.setParameter("id", movieId);
            movies.setParameter("date", date);
            return movies.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Cannot get sessions from db with id = " + movieId
                    + " date = " + date, e);
        }
    }
}
