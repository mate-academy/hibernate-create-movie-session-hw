package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> query = cb.createQuery(MovieSession.class);
            Root<MovieSession> root = query.from(MovieSession.class);
            root.fetch("movie", JoinType.LEFT);
            root.fetch("cinemaHall", JoinType.LEFT);
            query.where(cb.equal(root.get("id"), id));
            return Optional.ofNullable(session.createQuery(query).getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a movie session by id: " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startOfDay = date.atTime(LocalTime.MIN);
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> query = criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> root = query.from(MovieSession.class);
            root.fetch("movie", JoinType.LEFT);
            root.fetch("cinemaHall", JoinType.LEFT);
            Predicate movieIdPredicate = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate showTimeIdPredicateFrom = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("showTime"), startOfDay);
            Predicate showTimeIdPredicateTo = criteriaBuilder
                    .lessThanOrEqualTo(root.get("showTime"), endOfDay);
            query.where(movieIdPredicate,showTimeIdPredicateFrom,showTimeIdPredicateTo);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all available sessions from DB", e);
        }
    }
}
