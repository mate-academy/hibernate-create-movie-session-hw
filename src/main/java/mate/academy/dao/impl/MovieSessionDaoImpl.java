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
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session
                        .get(MovieSession.class, id));
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, ex);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> fromMovieSession = session
                    .createQuery("FROM MovieSession mv WHERE mv.showTime = :date ",
                            MovieSession.class);
            fromMovieSession.setParameter("date", date);
            return fromMovieSession.getResultList();
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get movieSession from DB", ex);
        }
    }
}
