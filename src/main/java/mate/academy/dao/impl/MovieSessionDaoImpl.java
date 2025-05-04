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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't add moviesession to DB " + movieSession, e);
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
            throw new DataProcessingException("Can't get moviesession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        String getAllSession = "from MovieSession m where m.movie.id = :movieId "
                + "and m.showTime >= :startDate and m.showTime <= :endDate";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailaibleMovieSessions =
                    session.createQuery(getAllSession, MovieSession.class);
            getAvailaibleMovieSessions.setParameter("movieId", movieId);
            getAvailaibleMovieSessions.setParameter("startDate", date.atStartOfDay());
            getAvailaibleMovieSessions.setParameter("endDate", date.atTime(23,59,59));
            return getAvailaibleMovieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions by id "
                    + movieId + " and date " + date, e);
        }
    }
}
