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
            Query<MovieSession> findMovieSessionByDateQuery = session.createQuery(
                    "FROM MovieSession ms "
                            + "WHERE ms.movie.id = :id "
                            + "AND year (ms.showTime) = :year "
                            + "AND month (ms.showTime) = :mounth "
                            + "AND day (ms.showTime) = :day", MovieSession.class);
            findMovieSessionByDateQuery.setParameter("id", movieId);
            findMovieSessionByDateQuery.setParameter("year", date.getYear());
            findMovieSessionByDateQuery.setParameter("mounth", date.getMonthValue());
            findMovieSessionByDateQuery.setParameter("day", date.getDayOfMonth());
            return findMovieSessionByDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available movie session by movie id: "
                    + movieId + "and date: " + date, e);
        }
    }
}
