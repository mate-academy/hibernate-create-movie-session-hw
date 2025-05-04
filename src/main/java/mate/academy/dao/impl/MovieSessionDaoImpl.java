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
            Query<MovieSession> getMovieSessionById = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                            + "where ms.id = :id",
                    MovieSession.class);
            getMovieSessionById.setParameter("id", id);
            return getMovieSessionById.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.movie.id = :id "
                            + "and year(ms.showTime) = :year "
                            + "and month(ms.showTime) = :month "
                            + "and day(ms.showTime) = :day",
                    MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("year", date.getYear());
            query.setParameter("month", date.getMonthValue());
            query.setParameter("day", date.getDayOfMonth());
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(String.format(
                    "Can't find movie session with parameters: %s, %s", movieId, date), e);
        }
    }
}
