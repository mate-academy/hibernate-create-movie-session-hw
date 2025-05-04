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
        int monthValue = date.getMonthValue();
        int dayValue = date.getDayOfMonth();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessions = session.createQuery(
                    "FROM MovieSession movieSession WHERE "
                            + "MONTH(movieSession.showTime) = :dBMonthValue AND "
                            + "DAY(movieSession.showTime) = :dBDayValue",
                    MovieSession.class);
            getAvailableSessions.setParameter("dBMonthValue", monthValue);
            getAvailableSessions.setParameter("dBDayValue", dayValue);
            return getAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't form a cinema halls list from DB", e);
        }
    }
}
