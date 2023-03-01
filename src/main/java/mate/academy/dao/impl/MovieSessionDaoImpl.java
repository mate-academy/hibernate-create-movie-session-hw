package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime localDateTimeFrom = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime localDateTimeTo = LocalDateTime.of(date, LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Query getAllMoviesQuery =
                    session.createQuery("from MovieSession ms where ms.showTime >=:dateFrom and "
                            + "ms.showTime <=:dateTo and "
                            + "ms.movie.id =:movie_id", MovieSession.class);
            getAllMoviesQuery.setParameter("dateFrom", localDateTimeFrom);
            getAllMoviesQuery.setParameter("dateTo", localDateTimeTo);
            getAllMoviesQuery.setParameter("movie_id", movieId);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can´t get all MovieSession by id" + movieId + "", e);
        }
    }
}
