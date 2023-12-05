package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionServiceDaoImpl implements MovieSessionServiceDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
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
            throw new DataProcessingException("Can't insert MovieSession " + movieSession, e);
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
            throw new DataProcessingException("Can't get a MovieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime localDateTimeStartOfDay = date.atStartOfDay();
        LocalDateTime localDateTimeEndOfDay = date.atTime(23,59,59);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms "
                            + "where ms.movie.id =: id "
                            + "and ms.localDateTime >=: dateStart "
                            + "and ms.localDateTime <=: dateEnd", MovieSession.class);
            getAllAvailableSessionsQuery.setParameter("id", movieId);
            getAllAvailableSessionsQuery.setParameter("dateStart", localDateTimeStartOfDay);
            getAllAvailableSessionsQuery.setParameter("dateEnd", localDateTimeEndOfDay);
            return getAllAvailableSessionsQuery.list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get Available Session by movie ID " + movieId
                    + " on Date " + date + " ",e);
        }
    }
}
