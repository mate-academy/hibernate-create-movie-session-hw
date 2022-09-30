package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save movie session to DB" + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie session by id=" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfWorkingCinema = date.atTime(0, 0, 0);
        LocalDateTime endOfWorkingCinema = date.atTime(23, 59, 59);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessionQuery =
                    session.createQuery("FROM MovieSession ms WHERE ms.movie.id = :movieId "
                    + "AND ms.showTime BETWEEN :start AND :end ", MovieSession.class);
            findAvailableSessionQuery.setParameter("movieId", movieId);
            findAvailableSessionQuery.setParameter("start", startOfWorkingCinema);
            findAvailableSessionQuery.setParameter("end", endOfWorkingCinema);
            return findAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't find movie session by id=" + movieId
                    + " at particular date=" + date, e);
        }
    }
}
