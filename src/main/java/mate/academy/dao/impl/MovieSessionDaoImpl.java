package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie " + movieSession, e);
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery =
                    session.createQuery("select m from MovieSession m where m.id =: value",
                            MovieSession.class);
            movieSessionQuery.setParameter("value", id);
            return movieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAllMoviesQuery = session.createQuery("from MovieSession", MovieSession.class);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cant get all MovieSession from db", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalTime localTimeFrom = LocalTime.of(0, 0, 0);
        LocalTime localTimeTo = LocalTime.of(23, 59, 59);
        LocalDateTime localDateTimeFrom = LocalDateTime.of(date, localTimeFrom);
        LocalDateTime localDateTimeTo = LocalDateTime.of(date, localTimeTo);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAllMoviesQuery =
                    session.createQuery("from MovieSession ms where ms.showTime >=:dateFrom and "
                    + "ms.showTime <=:dateTo and "
                    + "ms.movie.id =:movie_id", MovieSession.class);
            getAllMoviesQuery.setParameter("dateFrom", localDateTimeFrom);
            getAllMoviesQuery.setParameter("dateTo", localDateTimeTo);
            getAllMoviesQuery.setParameter("movie_id", movieId);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can´t get all MovieSession by id" + movieId + "", e);
        }
    }
}
