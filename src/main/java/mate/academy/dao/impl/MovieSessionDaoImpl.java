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
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t add movieSession " + movieSession, e);
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
            throw new DataProcessingException("Can`t get movieSession by id " + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvalibleSessionQuery = session.createQuery(
                    "from MovieSession ms where ms.showTime between :fromDateTime "
                            + "and :toDateTime and ms.movie.id = :movieId", MovieSession.class);
            getAllAvalibleSessionQuery.setParameter("fromDateTime",
                    LocalDateTime.of(date, LocalTime.MIN));
            getAllAvalibleSessionQuery.setParameter("toDateTime",
                    LocalDateTime.of(date, LocalTime.MAX));
            getAllAvalibleSessionQuery.setParameter("movieId", movieId);
            return getAllAvalibleSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find avaliable sessios for movie with id "
                    + movieId + " for " + date + " date", e);
        }
    }
}
