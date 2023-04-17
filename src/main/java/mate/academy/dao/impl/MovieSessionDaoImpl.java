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
            throw new DataProcessingException("Can't get a movie by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findMovieSessionByRequiredDateQuery = session.createQuery(
                    "from MovieSession ms "
                            + "where ms.movie.id = :id "
                            + "and year(ms.showTime) = :year "
                            + "and month(ms.showTime) = :month "
                            + "and day(ms.showTime) = :day", MovieSession.class);
            findMovieSessionByRequiredDateQuery.setParameter("id", movieId);
            findMovieSessionByRequiredDateQuery.setParameter("year", date.getYear());
            findMovieSessionByRequiredDateQuery.setParameter("month", date.getMonthValue());
            findMovieSessionByRequiredDateQuery.setParameter("day", date.getDayOfMonth());
            return findMovieSessionByRequiredDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't perform movie session search by id: "
                    + movieId + "and date: " + date, e);
        }
    }
}
