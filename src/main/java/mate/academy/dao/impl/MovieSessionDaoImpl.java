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
            session = HibernateUtil.getSessionFactory().openSession();
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall where ms.id = :id", MovieSession.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> availableSessions =
                    session.createQuery("from MovieSession ms "
                                    + "left join fetch ms.cinemaHall "
                                    + "left join fetch ms.movie m "
                                    + "where m.id = :movieId "
                                    + "and ms.showTime between :startOfTheDay and :endOfTheDay",
                            MovieSession.class);
            availableSessions.setParameter("movieId", movieId);
            availableSessions.setParameter("startOfTheDay", date.atStartOfDay());
            availableSessions.setParameter("endOfTheDay",
                    date.atTime(LocalTime.MAX));
            return availableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error while getting all movie sessions from DB.", e);
        }
    }
}
