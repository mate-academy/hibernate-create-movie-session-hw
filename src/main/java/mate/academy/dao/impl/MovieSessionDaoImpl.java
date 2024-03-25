package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.dto.MovieSessionsByMovieAndDate;
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
            throw new DataProcessingException("Can't save movie session.", e);
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
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sesssion wirth id: " + id);
        }
    }

    @Override
    public List<MovieSessionsByMovieAndDate> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = factory.openSession()) {
            Query<MovieSessionsByMovieAndDate> movies = session.createQuery(
                    "select new mate.academy.dto.MovieSessionsByMovieAndDate(ms) "
                    + "from MovieSession ms "
                    + "left join ms.movie m "
                    + "where m.id = :movieId and date(ms.dateTime) = :date",
                    MovieSessionsByMovieAndDate.class);
            movies.setParameter("movieId", movieId);
            movies.setParameter("date", date);
            return movies.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("No available sessions. ", e);
        }
    }
}
