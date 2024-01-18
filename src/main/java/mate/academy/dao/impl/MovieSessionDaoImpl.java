package mate.academy.dao.impl;

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
import java.time.LocalDate;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
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
            LocalDate localDateMin = LocalDate.from(LocalDateTime.of(date, LocalTime.MIN));
            LocalDate localDateMax = LocalDate.from(LocalDateTime.of(date, LocalTime.MAX));
            Query<MovieSession> fromMovieSession = session.createQuery(
                    "FROM MovieSession m "
                        + "WHERE m.movie.id = :id "
                        + "AND m.localDate > :localDateMin "
                        + "AND m.localDate < :localDateMax ",
                    MovieSession.class);
            fromMovieSession.setParameter("id", movieId);
            fromMovieSession.setParameter("localDateMin", localDateMin);
            fromMovieSession.setParameter("localDateMax", localDateMax);
            return fromMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of movie session: ", e);
        }
    }
}
