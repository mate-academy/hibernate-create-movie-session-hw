package mate.academy.dao.impl;

import java.time.LocalDate;
import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.model.MovieSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MovieSessionDaoImpl implements MovieSessionDao {

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = mate.academy.util.HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new mate.academy.exception.DataProcessingException("Can't insert movie session "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = mate.academy.util.HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new mate.academy.exception
                    .DataProcessingException("Can't get a movie session by id: "
                    + id, e);
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = mate.academy.util.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MovieSession", MovieSession.class).getResultList();
        } catch (Exception e) {
            throw new mate.academy.exception
                    .DataProcessingException("Can't get available sessions", e);
        }
    }
}
