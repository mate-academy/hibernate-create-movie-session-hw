package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session of "
                    + movieSession, e);
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
            Query getMovieSessionQuery = session.createQuery("from MovieSession ms"
                    + " left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall where ms.id = :id", MovieSession.class);
            return Optional.ofNullable((MovieSession) getMovieSessionQuery.setParameter("id", id)
                    .getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id "
            + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query avilableSessionsQuery = session.createQuery("from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall where ms.showTime"
                            + " between :dayStart and :dayEnd",
                    MovieSession.class);
            avilableSessionsQuery.setParameter("dayStart", date.atStartOfDay());
            avilableSessionsQuery.setParameter("dayEnd", date.atTime(LocalTime.MAX));
            return avilableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get session at movie id  " + movieId
                    + ", date " + date, e);
        }
    }
}
