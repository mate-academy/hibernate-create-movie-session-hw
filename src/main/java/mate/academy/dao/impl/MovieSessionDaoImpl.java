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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save movie session to DB ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session from DB ", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        LocalTime startDay = LocalTime.of(0, 0);
        LocalTime endDay = LocalTime.of(23, 59);
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> getAvailableSession = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :id and ms.showTime >= :start and ms.showTime <= :end",
                    MovieSession.class);
            getAvailableSession.setParameter("id", movieId);
            getAvailableSession.setParameter("start", LocalDateTime.of(date, startDay));
            getAvailableSession.setParameter("end", LocalDateTime.of(date, endDay));
            return getAvailableSession.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't find movie session from DB ", e);
        }
    }
}
