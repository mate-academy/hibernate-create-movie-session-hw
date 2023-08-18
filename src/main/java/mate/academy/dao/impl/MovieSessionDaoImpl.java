package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add movie session to DB: " + movieSession, e);
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
            Query<MovieSession> getMovieSessionQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie "
                    + "left join fetch ms.cinemaHall "
                    + "where ms.id = :id", MovieSession.class);
            getMovieSessionQuery.setParameter("id", id);
            return Optional.ofNullable(getMovieSessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movie session from DB by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> findSessionQuery = session.createQuery("from MovieSession ms "
                    + "left join fetch ms.movie m "
                    + "left join fetch ms.cinemaHall ch "
                    + "where m.id = :id and ms.showTime > :date", MovieSession.class);
            findSessionQuery.setParameter("id", movieId);
            findSessionQuery.setParameter("date", date.atStartOfDay());
            return checkDateFromSession(findSessionQuery.getResultList(), date);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find available session from DB by movie id: "
                    + movieId + ", and date: " + date, e);
        }
    }

    private List<MovieSession> checkDateFromSession(List<MovieSession> sessions, LocalDate date) {
        List<MovieSession> newSessions = new ArrayList<>();
        for (MovieSession session : sessions) {
            if (session.getShowTime().toLocalDate().equals(date)) {
                newSessions.add(session);
            }
        }
        return newSessions;
    }
}
