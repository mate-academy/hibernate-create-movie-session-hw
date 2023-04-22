package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            Query<MovieSession> sessionQuery = session
                    .createQuery("from MovieSession m "
                            + "join fetch m.movie mm where m.id = :id ", MovieSession.class);
            sessionQuery.setParameter("id", id);
            return Optional.ofNullable(sessionQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> sessionQuery = session
                    .createQuery("from MovieSession m join fetch m.movie mm "
                            + "where m.showTime = :date and mm.id = :movie_id ",
                            MovieSession.class);
            sessionQuery.setParameter("date", LocalDateTime.of(date.getYear(), date.getMonthValue(),
                    date.getDayOfMonth(), 0, 0));
            sessionQuery.setParameter("movie_id", movieId);
            return sessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of movie session "
                    + "with using movie id: "
                    + movieId + " and date time: " + date, e);
        }
    }
}
