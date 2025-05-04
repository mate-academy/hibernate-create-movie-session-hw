package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl extends AbstractDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add cinema-hall: " + cinemaHall + " to Database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get a cinema-hall by id: " + id + " from Database", e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM CinemaHall", CinemaHall.class)
                          .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema-halls from Database", e);
        }
    }
}
