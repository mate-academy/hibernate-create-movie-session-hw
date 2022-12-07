package mate.academy.dao.impl;

import java.time.LocalDate;
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
            throw new DataProcessingException("Can't create new movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query =
                    session.createQuery("FROM MovieSession AS ms "
                            + "LEFT JOIN FETCH ms.movie AS m "
                            + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                            + "WHERE ms.id = :id", MovieSession.class);
            return query.setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query =
                    session.createQuery("FROM MovieSession AS ms "
                            + "LEFT JOIN FETCH ms.movie AS m "
                            + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                            + "WHERE m.id = :id "
                            + "AND ms.showTime BETWEEN :startDay AND :endDay",
                            MovieSession.class);
            return query.setParameter("id", movieId)
                    .setParameter("startDay", date.atTime(LocalTime.MIN))
                    .setParameter("endDay", date.atTime(LocalTime.MAX))
                    .getResultList();
        }
    }
}
