package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    public MovieSessionDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movieSession " + movieSession, e);
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = factory.openSession()) {
            Query<MovieSession> getQueryMovieSession = session
                    .createQuery("from MovieSession ms "
                            + "left join fetch ms.movie where ms.id=:id", MovieSession.class);
            getQueryMovieSession.setParameter("id", id);
            return getQueryMovieSession.getSingleResult();
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        LocalDateTime startDay = date.atStartOfDay();
        LocalDateTime endDay = date.atTime(LocalTime.MAX);
        try (Session session = factory.openSession()) {
            Query<MovieSession> getQueryMovieSession = session
                    .createQuery("from MovieSession ms "
                            + "left join fetch ms.movie.id "
                            + "where ms.movie.id = :movieId "
                            + "and showTime between :start "
                            + " and :end", MovieSession.class);
            getQueryMovieSession.setParameter("movieId", movieId);
            getQueryMovieSession.setParameter("start", startDay);
            getQueryMovieSession.setParameter("end", endDay);
            return getQueryMovieSession.getResultList();
        }
    }
}
