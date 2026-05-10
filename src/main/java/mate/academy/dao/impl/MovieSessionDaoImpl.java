package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't insert movieSession" + movieSession, e);
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
            throw new DataProcessingException("Can't get movieSession by id" + id, e);
        }
    }

    @Override
    public List<MovieSession> getAllAvailableSessions(Long id, LocalDate date) {
        LocalDateTime localDateTimeBeginDay = date.atStartOfDay();
        LocalDateTime localDateTimeEndDay = localDateTimeBeginDay.plusDays(1).minusSeconds(1);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> fromMovieSession = session.createQuery("from MovieSession m "
                    + "WHERE m.movie.id = :id AND "
                    + "(m.showTime BETWEEN :t1 AND :t2)", MovieSession.class);
            fromMovieSession.setParameter("id", id);
            fromMovieSession.setParameter("t1", localDateTimeBeginDay);
            fromMovieSession.setParameter("t2", localDateTimeEndDay);
            return fromMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id" + id, e);
        }
    }
}
