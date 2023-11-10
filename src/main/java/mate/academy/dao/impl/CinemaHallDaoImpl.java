package mate.academy.dao.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    //is it good to do "try (session ...) { try { ...."?
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new DataProcessingException(String.format("Can't add "
                    + "cinema hall: %s to DB", cinemaHall), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return cinemaHall;
    }

    @Override
    public CinemaHall get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get "
                    + "cinema hall with id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            Query<CinemaHall> getAllCinemaHallQuery
                    = session.createQuery("from CinemaHall", CinemaHall.class);
            return getAllCinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("DB is empty", e);
        }
    }
}
