package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.MovieSession;
import mate.academy.service.MovieService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Inject
    private MovieService movieService;

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
            Query<MovieSession> getAllMovieSessionsQuery = session.createQuery(
                    "from MovieSession ms where ms.showTime "
                            + "between :startOfDay and :endOfDay and ms.movie = :movie",
                    MovieSession.class);
            getAllMovieSessionsQuery.setParameter("startOfDay", date.atTime(LocalTime.MIN));
            getAllMovieSessionsQuery.setParameter("endOfDay", date.atTime(LocalTime.MAX));
            getAllMovieSessionsQuery.setParameter("movie", movieService.get(movieId));
            return getAllMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie sessions from DB by movie's id "
                    + movieId + " and date " + date, e);
        }
    }
}
