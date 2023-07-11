package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            throw new DataProcessingException("Can't add movie session:" + movieSession, e);
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
            throw new DataProcessingException("Can't get movie session by id:" + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime startOfDate = LocalDateTime.of(date.getYear(),
                    date.getMonth(),date.getDayOfMonth(),0,0);
            LocalDateTime endOfDate = startOfDate.plusHours(23)
                    .plusMinutes(59).plusSeconds(59).plusNanos(999);
            Query<MovieSession> getAvailableSessionsQuery = session.createQuery(
                    "from MovieSession ms "
                            + "left join fetch ms.movie "
                            + "left join fetch ms.cinemaHall "
                            + "where ms.movie.id = :id "
                            + "AND ms.showTime >= :startTime AND ms.showTime <= :endTime",
                    MovieSession.class);
            getAvailableSessionsQuery.setParameter("startTime", startOfDate);
            getAvailableSessionsQuery.setParameter("endTime", endOfDate);
            getAvailableSessionsQuery.setParameter("id", movieId);
            return getAvailableSessionsQuery.getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get available sessions from db", e);
        }
    }
}
