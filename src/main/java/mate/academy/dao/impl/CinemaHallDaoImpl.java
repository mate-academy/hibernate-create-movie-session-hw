package mate.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
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
            throw new DataProcessingException("Can't insert cinema hall " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        Session session = null;
        List<CinemaHall> list = new ArrayList<>();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM CinemaHall";
            Query query = session.createQuery(hql);
            list.addAll((List<CinemaHall>) query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all cinema halls from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return list;
    }
}
