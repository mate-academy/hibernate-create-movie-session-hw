package mate.academy.dao.impl;

import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAvailableMovieSessionsQuery = session
                    .createQuery("from MovieSession c left join fetch c.movie m "
                            + "where m.id = :id ", MovieSession.class);
            getAvailableMovieSessionsQuery.setParameter("id", movieId);
            List<MovieSession> sessionsWithSameMovie =
                    getAvailableMovieSessionsQuery.getResultList();
            List<MovieSession> availableMovieSessions = new ArrayList<>();
            for (MovieSession movieSession: sessionsWithSameMovie) {
                if (movieSession.getShowTime().toLocalDate().equals(date)) {
                    availableMovieSessions.add(movieSession);
                }
            }
            return availableMovieSessions;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available movie sessions from DB", e);
        }
    }
}
