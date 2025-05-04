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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add movie session to DB " + movieSession, e);
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get from DB movie session with id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> findSessionQuery = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :id "
                    + "and ms.showTime BETWEEN :fromDate AND :toDate", MovieSession.class);
            findSessionQuery.setParameter("id", movieId);
            findSessionQuery.setParameter("fromDate", LocalDateTime.of(date, LocalTime.MIN));
            findSessionQuery.setParameter("toDate", LocalDateTime.of(date, LocalTime.MAX));
            return findSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find movie session with movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
