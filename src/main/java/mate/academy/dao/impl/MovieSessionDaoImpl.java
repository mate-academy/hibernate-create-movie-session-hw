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
import org.hibernate.HibernateException;
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert MovieSession: "
                    + movieSession + '.', e);
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
            return Optional.ofNullable(session.get(MovieSession.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a MovieSession by id: " + id + '.', e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessionsByDate(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findAvailableSessions = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "left join fetch ms.cinemaHall "
                    + "where m.id = :id "
                    + "and ms.showTime between :beginDateTime and :endDateTime",
                    MovieSession.class);
            findAvailableSessions.setParameter("id", movieId);
            findAvailableSessions.setParameter("beginDateTime", date.atTime(LocalTime.MIN));
            findAvailableSessions.setParameter("endDateTime", date.atTime(LocalTime.MAX));
            return findAvailableSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available MovieSessions with movieId: "
                    + movieId + " and date: " + date + '.', e);
        }
    }
}
