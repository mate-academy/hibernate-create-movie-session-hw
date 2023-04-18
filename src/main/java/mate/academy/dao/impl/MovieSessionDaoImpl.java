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
            throw new DataProcessingException("Can't add MovieSession to DB: " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MovieSession ms "
                    + "join fetch ms.cinemaHall "
                    + "join fetch ms.movie "
                    + "where ms.id = :id", MovieSession.class)
                    .setParameter("id", id).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession from DB by ID: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> avaibleSessionQuery = session.createQuery("from MovieSession ms "
                    + "join fetch ms.cinemaHall "
                    + "join fetch ms.movie "
                    + "where ms.movie.id = :movieId and ms.showTime "
                    + "between :start and :end", MovieSession.class);
            avaibleSessionQuery.setParameter("movieId", movieId);
            avaibleSessionQuery.setParameter("start", date.atStartOfDay());
            avaibleSessionQuery.setParameter("end", date.atTime(LocalTime.MAX));
            return avaibleSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find avaible MovieSession "
                    + "from DB by Movie ID: "
                    + movieId + " and date: " + date, e);
        }
    }
}
