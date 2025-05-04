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
public class CinemaHallDaoImpl extends AbstractDao implements CinemaHallDao {

    public CinemaHallDaoImpl() {
        super(HibernateUtil.getSessionFactory());
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot add cinemaHall:" + cinemaHall, e);
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
            CinemaHall cinemaHall = session.get(CinemaHall.class, id);
            if (cinemaHall != null) {
                return cinemaHall;
            }
            throw new RuntimeException("Cannot find cinemaHall with id: " + id);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from CinemaHall").list();
        }
    }
}
