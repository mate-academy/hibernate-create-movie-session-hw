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
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session to db" + movieSession, e);
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
    public List<MovieSession> findAvailableSession(Long movieId, LocalDate date) {
        LocalDateTime beginningOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        String hql = "from MovieSession ms "
                + "left join fetch ms.movie msm "
                + "where ms.showTime between :beginningOfDay and :endOfDay "
                + "and msm.id = :movieId";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> movieSessionQuery = session.createQuery(hql, MovieSession.class);
            movieSessionQuery.setParameter("beginningOfDay", beginningOfDay);
            movieSessionQuery.setParameter("endOfDay", endOfDay);
            movieSessionQuery.setParameter("movieId", movieId);
            return movieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find any session for movie with id " + movieId
                                                + " on date " + date, e);
        }
    }
}
