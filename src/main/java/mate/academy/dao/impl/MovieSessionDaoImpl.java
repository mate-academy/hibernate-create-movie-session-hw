package mate.academy.dao.impl;

import mate.academy.dao.MovieSessionDao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("cant add movie session: " + movieSession, e);
        } finally {
            if (session != null) {

            }
        }
        return movieSession;
    }

    @Override
    public Optional<MovieSession> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MovieSession> getAllAvailableMovieSessions() {
        return null;
    }
}
