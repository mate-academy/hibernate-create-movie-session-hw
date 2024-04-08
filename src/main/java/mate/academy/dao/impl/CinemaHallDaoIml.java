package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoIml implements CinemaHallDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to add cinema hall to DB " + cinemaHall, e);
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get CinemaHall by id " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery("SELECT * FROM cinema_halls", CinemaHall.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to fetch all the CinemaHalls from BD", e);
        }
    }
}
