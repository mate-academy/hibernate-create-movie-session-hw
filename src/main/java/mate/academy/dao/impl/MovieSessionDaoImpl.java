package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
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
            throw new DataProcessingException("Can't save movie session", e);
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
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> resultList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String query = "from MovieSession ms WHERE ms.movie.id = :movieID AND ms.showtime BETWEEN :dateFrom AND :dateTO ";
            Query<MovieSession> getAllCinemaHalls = session.createQuery(query, MovieSession.class);
            getAllCinemaHalls.setParameter("movieID", movieId);
            getAllCinemaHalls
                    .setParameter("dateFrom", LocalDateTime.of(date, LocalTime.MIN));
            getAllCinemaHalls
                    .setParameter("dateTO", LocalDateTime.of(date, LocalTime.MAX));
            resultList = getAllCinemaHalls.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all cinema halls", e);
        }
        return resultList;
    }
}
