package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);

    @Override
    public MovieSession add(MovieSession movieSession) {
        CinemaHall cinemaHall = movieSession.getCinemaHall();
        if (cinemaHall.getId() == null) {
            cinemaHallService.add(cinemaHall);
        }
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
            throw new DataProcessingException("Can't insert Session " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a Session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> result = session.createQuery("FROM MovieSession ms "
                            + "WHERE ms.movie = :movieId AND ms.showTime "
                    + "BETWEEN :startOfDay AND :endOfDay", MovieSession.class);
            result.setParameter("movieId", movieService.get(movieId));
            LocalDateTime startOfDay = date.atStartOfDay();
            result.setParameter("startOfDay", startOfDay);
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
            result.setParameter("endOfDay", endOfDay);
            return result.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed for Session by method findAvailableSessions() ", e);
        }
    }
}
