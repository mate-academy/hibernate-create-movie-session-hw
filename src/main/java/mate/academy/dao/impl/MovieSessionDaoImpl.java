package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {

    protected MovieSessionDaoImpl (SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
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
        try (Session session = factory.openSession()) {
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
        try (Session session = factory.openSession()) {
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
