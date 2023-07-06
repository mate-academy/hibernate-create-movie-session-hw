package mate.academy.dao.cinema.hall.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.cinema.hall.CinemaHallDao;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add CinemaHall: " + cinemaHall + " to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CinemaHall cinemaHall = session.get(CinemaHall.class, id);
            return Optional.ofNullable(cinemaHall);
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get CinemaHall with id: " + id + " from DB.", e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> movieSessions =
                    session.createQuery("from CinemaHall", CinemaHall.class);
            return movieSessions.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all CinemaHall from DB", e);
        }
    }
}
