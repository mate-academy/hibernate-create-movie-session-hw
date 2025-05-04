package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        try (var session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinema hall " + cinemaHall, ex);
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (var session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception ex) {
            throw new DataProcessingException("Can't get a cinema hall by id: " + id, ex);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("FROM CinemaHall", CinemaHall.class).getResultList();
        } catch (Exception ex) {
            throw new DataProcessingException("Can`t get all cinema halls from DB", ex);
        }
    }
}
