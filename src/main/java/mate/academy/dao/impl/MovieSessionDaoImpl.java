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
            throw new DataProcessingException("Can't add a movie session: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery("from MovieSession m "
                    + "left join fetch m.movie "
                    + "left join fetch m.cinemaHall "
                    + "where m.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findSessionQuery = session.createQuery("from MovieSession m "
                    + "left join fetch m.movie "
                    + "left join fetch m.cinemaHall "
                    + "where m.movie.id = :movieId "
                    + "and m.showTime between :fromDate and :toDate", MovieSession.class);
            findSessionQuery.setParameter("movieId", movieId);
            findSessionQuery.setParameter("fromDate", date.atStartOfDay());
            findSessionQuery.setParameter("toDate", date.atTime(LocalTime.MAX));
            return findSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions list", e);
        }
    }
}
