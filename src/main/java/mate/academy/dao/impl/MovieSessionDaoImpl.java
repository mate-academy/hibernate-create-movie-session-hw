package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {

    public MovieSessionDaoImpl() {
        super(HibernateUtil.getSessionFactory());
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot add MovieSession", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = factory.openSession()) {
            MovieSession movieSession = session.get(MovieSession.class, id);
            if (movieSession != null) {
                return movieSession;
            }
            throw new RuntimeException("Cannot get MovieSession with id: " + id);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {

        try (Session session = factory.openSession()) {
            Query<MovieSession> query = session.createQuery(
                    "FROM MovieSession ms "
                            + "WHERE ms.movie.id = :movieId "
                            + "AND ms.showTime BETWEEN :start AND :end",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("start", date.atStartOfDay()); // 00:00:00
            query.setParameter("end", date.atTime(23, 59, 59));

            return query.getResultList();
        }
    }
}
