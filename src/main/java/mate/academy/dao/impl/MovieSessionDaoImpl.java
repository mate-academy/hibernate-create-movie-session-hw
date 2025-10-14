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
            throw new DataProcessingException(
                    "Can't insert movieSession " + movieSession + ", "
                            + "transaction roll back.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            MovieSession movieSession = session.find(MovieSession.class, id);
            if (movieSession != null) {
                return Optional.of(movieSession);
            }
        } catch (RuntimeException e) {
            throw new DataProcessingException(
                    "Can't get movieSession with id " + id + " from database", e);
        }
        return Optional.empty();
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfInsertDate = LocalDateTime.of(
                date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endOfInsertDate = LocalDateTime.of(
                date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession m "
                    + " WHERE m.id = : id AND m.showTime BETWEEN : d AND : m ", MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("d", startOfInsertDate);
            query.setParameter("m", endOfInsertDate);

            return query.getResultList();
        }
    }
}
