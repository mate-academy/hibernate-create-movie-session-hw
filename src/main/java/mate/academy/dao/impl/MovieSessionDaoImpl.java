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
    private static final String MOVIE_ID = "movieId";
    private static final String DAY_BEGIN = "dayBegin";
    private static final String DAY_END = "dayEnd";

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
        String hqlQuery = "FROM MovieSession m WHERE movie.id = :" + MOVIE_ID
                + "AND showTime > :" + DAY_BEGIN
                + "AND showTime < :" + DAY_END;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findSessionsQuery = session.createQuery(hqlQuery);
            findSessionsQuery.setParameter(MOVIE_ID, movieId);
            findSessionsQuery.setParameter(DAY_BEGIN, LocalDateTime.of(date, LocalTime.MIN));
            findSessionsQuery.setParameter(DAY_END, LocalDateTime.of(date, LocalTime.MAX));
            return findSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find any movie sessions.", e);
        }
    }
}
