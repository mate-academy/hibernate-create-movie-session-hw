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
    private static final int ZERO_HOURS = 0;
    private static final int ZERO_MINUTES = 0;
    private static final int ZERO_SECONDS = 0;
    private static final int MAX_HOUR = 23;
    private static final int MAX_MINUTE = 59;
    private static final int MAX_SECOND = 59;

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
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
            Query<MovieSession> getMovieSessionsQuery =
                    session.createQuery("from MovieSession s "
                                    + "where s.id = :movieId "
                                    + "and s.showTime > :dateTimeFrom "
                                    + "and s.showTime < :dateTimeTo",
                            MovieSession.class);
            getMovieSessionsQuery.setParameter("movieId", movieId);
            getMovieSessionsQuery.setParameter("dateTimeFrom",
                    date.atTime(ZERO_HOURS,ZERO_MINUTES, ZERO_SECONDS));
            getMovieSessionsQuery.setParameter("dateTimeTo",
                    date.atTime(MAX_HOUR,MAX_MINUTE, MAX_SECOND));
            return getMovieSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    String.format("Can't get a movie session list with id = " + movieId
                            + " and date = " + date), e);
        }
    }
}
