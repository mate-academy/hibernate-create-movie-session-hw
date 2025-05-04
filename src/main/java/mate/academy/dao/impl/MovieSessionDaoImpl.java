package mate.academy.dao.impl;

import static mate.academy.util.HibernateUtil.getSessionFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
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
            session = getSessionFactory().openSession();
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
        try (Session session = getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByIdQuery
                    = session.createQuery("from MovieSession ms join fetch ms.movie "
                    + "join fetch ms.cinemaHall where ms.id = :id", MovieSession.class);
            getMovieSessionByIdQuery.setParameter("id", id);
            return getMovieSessionByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> getByMovieIdAndDate(Long movieId, LocalDate date) {
        try (Session session = getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionsByIdAndDateQuery
                    = session.createQuery("from MovieSession ms "
                    + "join fetch ms.movie join fetch ms.cinemaHall "
                    + "where ms.movie.id = :movieId "
                    + "and ms.showTime between :startOfDate and :endOfDate ", MovieSession.class);
            getAllMovieSessionsByIdAndDateQuery.setParameter("movieId", movieId);
            getAllMovieSessionsByIdAndDateQuery.setParameter("startOfDate", date.atStartOfDay());
            getAllMovieSessionsByIdAndDateQuery.setParameter("endOfDate", date.atTime(23,59,59));
            return getAllMovieSessionsByIdAndDateQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions by movie id = "
                    + movieId + " and date = " + date + " from DB", e);
        }
    }
}
