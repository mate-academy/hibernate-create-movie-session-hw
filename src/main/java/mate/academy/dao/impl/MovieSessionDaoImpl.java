package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            throw new DataProcessingException("Can't create movie session " + movieSession, e);
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
            throw new DataProcessingException("Can't get from DB a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime dateTimeStart = LocalDateTime.of(date.getYear(),
                    date.getMonth(),
                    date.getDayOfMonth(), 0, 0);
            LocalDateTime dateTimeEnd = dateTimeStart.plusHours(23).plusMinutes(59)
                    .plusSeconds(59).plusNanos(999999999);
            Query<MovieSession> getAvailableSessionQuery = session.createQuery(
                    "from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.movie.id = :id "
                    + "AND ms.showTime >= :startTime AND ms.showTime <= :endTime",
                    MovieSession.class);
            getAvailableSessionQuery.setParameter("startTime", dateTimeStart);
            getAvailableSessionQuery.setParameter("endTime", dateTimeEnd);
            getAvailableSessionQuery.setParameter("id", movieId);
            return getAvailableSessionQuery.getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get from DB available sessions from DB", e);
        }
    }
}
