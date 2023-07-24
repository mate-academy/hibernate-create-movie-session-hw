package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = getSession();
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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
        try (Session session = getSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = getSession()) {
            Query<CinemaHall> getAllCinemaHall =
                    session.createQuery("from CinemaHall ", CinemaHall.class);
            return getAllCinemaHall.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("can`t get all cinema halls from db", e);
        }
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}

