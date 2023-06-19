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
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session  " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return Optional.of(getMovieSessionQuery.getSingleResult());

        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        List<MovieSession> resultList;
        LocalTime beganTime = LocalTime.of(00, 00, 00);
        LocalDateTime fromDateTime = LocalDateTime.of(date, beganTime);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> allCinemaHallQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.showTime > :value1 AND showTime < :value2", MovieSession.class);
            allCinemaHallQuery.setParameter("value1", fromDateTime);
            allCinemaHallQuery.setParameter("value2", fromDateTime.plusDays(1L));
            resultList = allCinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Cinema Hall's from DB", e);
        }
        return resultList;
    }
}
