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
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public CinemaHall get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> fromCinemaHall
                    = session.createQuery("from CinemaHall", CinemaHall.class);
            return fromCinemaHall.getResultList();
        }
    }
}
