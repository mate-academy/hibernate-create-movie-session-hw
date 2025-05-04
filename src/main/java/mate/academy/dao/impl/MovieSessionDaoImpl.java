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
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(movieSession);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert a movie session " + movieSession, e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getMovieSessionByMovieIdAndDateQuery =
                    session.createQuery("FROM MovieSession "
                    + "WHERE movie.id = :movieId "
                    + "AND DATE(showTime) = :showDate", MovieSession.class);
            getMovieSessionByMovieIdAndDateQuery.setParameter("movieId", movieId);
            getMovieSessionByMovieIdAndDateQuery.setParameter("showDate", date);

            return getMovieSessionByMovieIdAndDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions!", e);
        }
    }
}
