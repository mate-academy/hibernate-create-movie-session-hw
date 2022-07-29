package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
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
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("cant add movie session: " + movieSession, e);
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
            throw new RuntimeException("cant get movie session with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAllAvailableMovieSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableMovieSession = session.createQuery("FROM MovieSession "
                    + "ms WHERE ms.movie.id = :id "
                    + "AND ms.showTime BETWEEN :dayBegin AND :dayFinish", MovieSession.class);
            getAvailableMovieSession.setParameter("id", movieId);
            getAvailableMovieSession.setParameter("dayBegin", date.atTime(LocalTime.MIN));
            getAvailableMovieSession.setParameter("dayFinish", date.atTime(LocalTime.MAX));
            return getAvailableMovieSession.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("cant get movie sessions for movie: " + movieId
                    + " for chosen date: " + date.format(DateTimeFormatter.ofPattern("DD.MM")), e);
        }
    }
}
