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
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't save movieSession "
                    + movieSession + " in DB", e);
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
            throw new DataProcessingException("Can't get movieSession with id "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query findSessionQuery = session.createQuery("from MovieSession ms "
                    + "where ms.movie.id = :id "
                    + "and ms.showTime between :daysStart "
                    + "and :daysEnd");
            findSessionQuery.setParameter("id", movieId);
            findSessionQuery.setParameter("daysStart", date.atStartOfDay());
            findSessionQuery.setParameter("daysEnd", date.atTime(LocalTime.MAX));
            List listOfMovieSessions = findSessionQuery.getResultList();
            return listOfMovieSessions;
        } catch (Exception e) {
            throw new DataProcessingException("Can't find in DB movieSession with id " + movieId
            + " and in date of date " + date, e);
        }
    }
}
