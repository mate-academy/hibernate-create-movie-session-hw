package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert CinemaHall.", e);
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
            return Optional.of(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllCommentQuery
                    = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "left join fetch ms.cinemaHall "
                    + "WHERE m.id = :movieId "
                    + "AND ms.showTime = :dayOfYear AND ms.showTime = :year", MovieSession.class);
            getAllCommentQuery.setParameter("movieId", movieId);
            getAllCommentQuery.setParameter("dayOfYear", date.getDayOfYear());
            getAllCommentQuery.setParameter("year", date.getYear());
            return getAllCommentQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all MovieSessions.", e);
        }
    }
}
