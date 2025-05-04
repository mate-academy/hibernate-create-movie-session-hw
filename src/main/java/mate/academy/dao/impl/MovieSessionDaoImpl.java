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
    @Override
    public MovieSession add(MovieSession movieSession) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new DataProcessingException("Can't insert movie session " + movieSession, e);
            }
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
            throw new DataProcessingException("Can't get movie session by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionListByMovieAndDate = session.createQuery(
                    "from MovieSession ms where ms.id =: movieId "
                            + "and ms.showTime BETWEEN :startTime AND :endTime",
                    MovieSession.class);
            getMovieSessionListByMovieAndDate.setParameter("movieId", movieId);
            getMovieSessionListByMovieAndDate.setParameter("startTime", date.atStartOfDay());
            getMovieSessionListByMovieAndDate.setParameter("endTime", date.atTime(23,59,59));
            return getMovieSessionListByMovieAndDate.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of movie session on ", e);
        }
    }
}
