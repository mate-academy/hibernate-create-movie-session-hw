package mate.academy.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    private static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);

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
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSessionByIdQuery =
                    session.createQuery("FROM MovieSession m "
                        + "JOIN FETCH m.movie "
                        + "JOIN FETCH m.cinemaHall "
                        + "WHERE m.id = :id ", MovieSession.class);
            getMovieSessionByIdQuery.setParameter("id", id);
            return getMovieSessionByIdQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get movieSession by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> criteriaQuery =
                    criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> fromRoot = criteriaQuery.from(MovieSession.class);
            Predicate moviePredicate = criteriaBuilder.equal(fromRoot.get("movie"), movieId);
            Predicate datePredicate = criteriaBuilder.between(fromRoot.get("showTime"),
                    date.atStartOfDay(), date.atTime(END_OF_DAY));
            Predicate movieAndDatePredicate = criteriaBuilder.and(moviePredicate, datePredicate);
            criteriaQuery.select(fromRoot).where(movieAndDatePredicate);
            fromRoot.fetch("movie");
            fromRoot.fetch("cinemaHall");
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get available sessions. "
                    + "movie id: "
                    + movieId
                    + ". Date: " + date, e);
        }
    }
}
