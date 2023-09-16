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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movieSession to DB", e);
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
            Query<MovieSession> getMovieSessionQuery =
                    session.createQuery("FROM movie_sessions AS ms "
                            + "LEFT JOIN FETCH ms.movie AS m "
                            + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                            + "WHERE m.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailabSessionsQuery =
                    session.createQuery("FROM movie_sessions AS ms "
                                    + "LEFT JOIN FETCH ms.movie AS m "
                                    + "LEFT JOIN FETCH ms.cinemaHall AS ch "
                                    + "WHERE m.id = :id AND CAST(ms.showTime AS DATE) = :date",
                            MovieSession.class);;
            findAvailabSessionsQuery.setParameter("id", movieId);
            findAvailabSessionsQuery.setParameter("date", date);
            return findAvailabSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find availab sessions", e);
        }
    }
}
