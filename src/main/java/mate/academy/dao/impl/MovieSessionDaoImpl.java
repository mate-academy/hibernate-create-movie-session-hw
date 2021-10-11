package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
            throw new DataProcessingException("Can't get movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> get(Long movieId, LocalDate localDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM MovieSession WHERE movie.id = :movie_id AND DATE(showTime) = :date";
            Query query = session.createQuery(hql);
            query.setParameter("movie_id", movieId);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            query.setParameter("date", date);
            return (List<MovieSession>) query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions", e);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MovieSession", MovieSession.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie sessions", e);
        }
    }
}
