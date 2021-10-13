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
        String availableSessions = "FROM MovieSession m LEFT JOIN FETCH m.movie "
                + "where m.movie.id = :movieId and m.showTime between :dateFrom and :dateTo";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery(availableSessions);
            query.setParameter("dateFrom", date.atStartOfDay());
            query.setParameter("dateTo", date.atStartOfDay().plusDays(1));
            query.setParameter("movieId", movieId);
            List resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions "
                    + "by a movie id: " + movieId, e);
        }
    }
}
