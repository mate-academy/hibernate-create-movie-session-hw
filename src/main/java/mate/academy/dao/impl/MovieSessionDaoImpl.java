package mate.academy.dao.impl;

import java.util.List;
import mate.academy.dao.MovieSessionDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
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
            throw new RuntimeException("Can't add movie session with film"
                    + movieSession.getMovie() + " to DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movieSession;
    }

    @Override
    public MovieSession get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MovieSession.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get movie session with id " + id);
        }
    }

    @Override
    public List<MovieSession> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MovieSession", MovieSession.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all movie sessions!");
        }
    }
}
