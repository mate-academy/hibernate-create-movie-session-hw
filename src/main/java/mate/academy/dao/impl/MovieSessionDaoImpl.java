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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> findAvailableSessionQuery = session
                    .createQuery("FROM MovieSession ms "
                            + "WHERE movie.id =: movieId "
                            + "AND YEAR (ms.showTime) =: year "
                            + "AND MONTH (ms.showTime) =: month "
                            + "AND DAY (ms.showTime) =: day", MovieSession.class);

            findAvailableSessionQuery.setParameter("movieId", movieId);
            findAvailableSessionQuery.setParameter("year", date.getYear());
            findAvailableSessionQuery.setParameter("month", date.getMonthValue());
            findAvailableSessionQuery.setParameter("day", date.getDayOfMonth());
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get available session from DB", e);
        }
    }
}
