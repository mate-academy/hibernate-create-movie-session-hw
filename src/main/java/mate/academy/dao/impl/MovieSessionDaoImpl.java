package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
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
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert a movie session hall "
                    + movieSession, e);
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
        List<MovieSession> movieSessions = new ArrayList<>();
        if (date.isBefore(LocalDate.now())) {
            return movieSessions;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieQuery = session.createQuery("from MovieSession "
                    + "where id= :id and show_time between :fromTime and :tillTime",
                    MovieSession.class);
            movieQuery.setParameter("id", movieId);
            movieQuery.setParameter("fromTime", date.atTime(LocalTime.MIN));
            movieQuery.setParameter("tillTime", date.atTime(LocalTime.MAX));
            return movieQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie sessions", e);
        }
    }
}
