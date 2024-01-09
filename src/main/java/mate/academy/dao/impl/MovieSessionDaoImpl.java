package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add movieSession " + movieSession, e);
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
                    "from MovieSession m "
                            + "left join fetch m.cinemaHall "
                            + "left join fetch m.movie "
                            + "where m.id = :id");
            getMovieSessionQuery.setParameter("id", id);
            return getMovieSessionQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession by ID: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession()) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);
            Query<MovieSession> findAvailableSessionsQuery = session.createQuery(
                    "from MovieSession m "
                            + "where m.showTime >= :startOfDay "
                            + "and m.showTime <= :endOfDay "
                            + "and m.movie.id = :movieId",
                    MovieSession.class);
            findAvailableSessionsQuery.setParameter("startOfDay", startOfDay);
            findAvailableSessionsQuery.setParameter("endOfDay", endOfDay);
            findAvailableSessionsQuery.setParameter("movieId", movieId);
            List<MovieSession> resultList = findAvailableSessionsQuery.getResultList();
            resultList.sort(Comparator.comparing(MovieSession::getShowTime));
            return resultList;
        } catch (Exception e) {
            throw new RuntimeException("Can't find available sessions for movieId: " + movieId, e);
        }
    }
}
