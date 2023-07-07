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
            Query<MovieSession> getMovieSessionByIdQuery =
                    session.createQuery("from MovieSession ms "
                                        + "left join fetch ms.cinemaHall "
                                        + "left join fetch ms.movie "
                                        + "where ms.id = :id", MovieSession.class);
            getMovieSessionByIdQuery.setParameter("id", id);
            return getMovieSessionByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "left join fetch ms.cinemaHall ch "
                    + "where m.id = :id "
                    + "AND extract(year from ms.showTime) = :year and "
                    + "extract(month from ms.showTime) = :month "
                    + "and extract(day from ms.showTime) = :day "
            );
            findAvailableSessionsQuery.setParameter("id", movieId);
            findAvailableSessionsQuery.setParameter("year", year);
            findAvailableSessionsQuery.setParameter("month", month);
            findAvailableSessionsQuery.setParameter("day", day);
            return findAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't find available sessions on date"
                    + date + "for movie with id" + "movieId" + e);
        }
    }
}
