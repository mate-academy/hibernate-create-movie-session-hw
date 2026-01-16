package mate.academy.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MovieSessionDaoImpl extends AbstractDao implements MovieSessionDao {
    protected MovieSessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction tr = null;
        try {
            session = sessionFactory.openSession();
            tr = session.beginTransaction();
            session.persist(movieSession);
            tr.commit();

        } catch (DataProcessingException e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Cant add movie session", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MovieSession.class, id);
        } catch (DataProcessingException e) {
            throw new RuntimeException("Cant get movie session from DB", e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            Query<MovieSession> query = session.createQuery(
                    "from MovieSession ms where ms.movie.id = :id"
                    + " and ms.showTime between :start and :end", MovieSession.class);
            query.setParameter("id", movieId);
            query.setParameter("start", start);
            query.setParameter("end", end);

            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of available session: ", e);
        }
    }
}
