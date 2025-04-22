package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot save movie session", e);
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery(
                    "from MovieSession m "
                        + "where m.movie.id = :movieId "
                        + "and m.showTime > :start and m.showTime < :end",
                    MovieSession.class);
            getMovieSessionQuery.setParameter("movieId", movieId);
            getMovieSessionQuery.setParameter("start", date.atStartOfDay());
            getMovieSessionQuery.setParameter("end", date.atTime(23, 59, 59));
            return getMovieSessionQuery.list();
        }
    }
}
