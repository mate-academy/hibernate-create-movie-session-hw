package mate.academy.dao.impl;

import java.util.List;
import mate.academy.CinemaHall;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
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
            throw new DataProcessingException("Can't save cinema hall" + cinemaHall.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return cinemaHall;
    }

    @Override
    public CinemaHall get(Long id) {
        CinemaHall cinemaHall = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            cinemaHall = session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall" + id.toString(), e);
        }

        if (cinemaHall == null) {
            throw new DataProcessingException("Cinema hall is null" + id.toString(), null);
        }

        return cinemaHall;
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> query = session.createQuery("from CinemaHall", CinemaHall.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}
