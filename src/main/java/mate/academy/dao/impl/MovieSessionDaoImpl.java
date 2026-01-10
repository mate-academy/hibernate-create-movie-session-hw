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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save movieSession to database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery(
                    "from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);

            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableSessionQuery = session.createQuery(
                    "from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.movie.id = :movieId "
                            + "AND ms.showTime >= :startDiapason AND ms.showTime < :endDiapason",
                    MovieSession.class);

            getAvailableSessionQuery.setParameter("movieId", movieId);
            getAvailableSessionQuery.setParameter("startDiapason",
                    date.atStartOfDay());
            getAvailableSessionQuery.setParameter("endDiapason",
                    date.plusDays(1).atStartOfDay());

            return getAvailableSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find all availableSessions by date "
                    + date, e);
        }
    }
}
