package mate.academy.dao.impl;

import java.util.List;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert CinemaHall to DB " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
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
        Session session = null;
        Transaction transaction = null;
        List<CinemaHall> cinemaHalls;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            cinemaHalls = session.createQuery("SELECT a from CinemaHall a",
                    CinemaHall.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get all cinemaHalls from DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHalls;
    }
}
