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
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(movieSession);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DataProcessingException("cant save movie session: "
                    + movieSession.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("cant get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessions = session.createQuery(
                    "from MovieSession ms join fetch ms.movie "
                            + "where ms.movie.id = :movieId"
                            + " and date(ms.showTime) = :date ", MovieSession.class);
            movieSessions.setParameter("movieId", movieId);
            movieSessions.setParameter("date", date);
            return movieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get available movie sessions", e);
        }
    }
}
