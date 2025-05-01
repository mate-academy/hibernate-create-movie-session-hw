package mate.academy.dao.impl;

import java.time.LocalDateTime;
import java.util.List;
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
    public MovieSession add(MovieSession sessionObj) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(sessionObj);
            tx.commit();
            return sessionObj;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DataProcessingException("Can't insert movie session "
                    + sessionObj, e);
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session by id "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime from,
                                                    LocalDateTime to) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM MovieSession ms "
                    + "WHERE ms.movie.id = :movieId "
                    + "AND ms.showTime BETWEEN :start AND :end";
            Query<MovieSession> query = session.createQuery(hql,
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("start", from);
            query.setParameter("end", to);
            return query.list();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Error fetching available sessions for movie "
                            + movieId, e);
        }
    }
}
