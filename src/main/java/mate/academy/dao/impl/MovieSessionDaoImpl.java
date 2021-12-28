package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateError;
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
        } catch (HibernateError error) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add movieSession" + movieSession, error);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (HibernateError error) {
            throw new RuntimeException("Can't get movieSession by id" + id, error);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                            + "where cast(ms.showTime as LocalDate) = :date AND ms.movie.id = :id",
                    MovieSession.class);
            query.setParameter("date", date);
            query.setParameter("id", movieId);
            return query.getResultList();
        } catch (HibernateError error) {
            throw new RuntimeException("Can't get all movieSessions by id - " + movieId + "and date - " + date, error);
        }
    }
}
