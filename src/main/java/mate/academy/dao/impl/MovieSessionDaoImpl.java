package mate.academy.dao.impl;

import java.time.LocalDate;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session with "
                    + movieSession.getMovie().getTitle() + " movie to DB", e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: "
                    + id + " in DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime timeStarts = date.atStartOfDay();
        LocalDateTime timeEnds = timeStarts.plusHours(23).plusMinutes(59).plusSeconds(59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessions = session.createQuery("from MovieSession ms"
                            + " where ms.id = :id and ms.showTime between :start and :end",
                    MovieSession.class);
            movieSessions.setParameter("id", movieId);
            movieSessions.setParameter("start", timeStarts);
            movieSessions.setParameter("end", timeEnds);
            return movieSessions.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available sessions for movie id: "
                    + movieId + " and date: " + date + " in DB", e);
        }
    }
}
