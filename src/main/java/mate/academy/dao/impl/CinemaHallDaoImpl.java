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
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = HibernateUtil.getSessionFactory().openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinema hall: " + cinemaHall, e);
        } finally {
            currentSession.close();
        }
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session currentSession = HibernateUtil.getSessionFactory().openSession();) {
            return Optional.ofNullable(currentSession.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session currentSession = HibernateUtil.getSessionFactory().openSession();) {
            Query<CinemaHall> getAllCinemaHallsQuery =
                    currentSession.createQuery("FROM CinemaHall ", CinemaHall.class);
            return getAllCinemaHallsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}
