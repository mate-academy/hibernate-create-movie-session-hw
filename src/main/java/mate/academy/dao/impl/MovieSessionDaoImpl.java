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
            session = getSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
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
        try (Session session = getSession()) {
            Query<MovieSession> allMovieSession = session.createQuery("from MovieSession ms "
                    + " left join fetch ms.cinemaHall "
                    + " left join fetch ms.movie m "
                    + " where ms.id = :id", MovieSession.class);
            allMovieSession.setParameter("id", id);
            return allMovieSession.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> availableSession(Long movieId, LocalDate date) {
        try (Session session = getSession()) {
            Query<MovieSession> allMovieSession = session.createQuery("from MovieSession ms"
                    + " left join fetch ms.cinemaHall"
                    + " left join fetch ms.movie "
                    + "where ms.localDateTime between :beginDateTime and :endDateTime"
                    + " and ms.movie.id = :movieId", MovieSession.class);
            allMovieSession.setParameter("beginDateTime", date.atStartOfDay());
            allMovieSession.setParameter("endDateTime", date.atTime(LocalTime.MAX));
            allMovieSession.setParameter("movieId", movieId);
            return allMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("can`t get movie session from db", e);
        }
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
