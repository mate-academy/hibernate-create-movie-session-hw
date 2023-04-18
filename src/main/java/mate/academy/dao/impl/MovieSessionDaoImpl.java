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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAvailableQuery =
                    session.createQuery("select ms from MovieSession ms "
                            + "left join ms.movie "
                            + "left join ms.cinemaHall "
                            + "where ms.movie.id = :movieId "
                            + "and ms.showTime >= :date and ms.showTime < : plusDay",
                            MovieSession.class);
            getAvailableQuery.setParameter("movieId", movieId);
            getAvailableQuery.setParameter("date", date.atStartOfDay());
            getAvailableQuery.setParameter("plusDay", date.plusDays(1).atStartOfDay());
            return getAvailableQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie sessions from DB", e);
        }
    }
}
