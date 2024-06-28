package mate.academy.dao.impl;

import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.CinemaHall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CinemaHallDaoImpl extends AbstractDao implements CinemaHallDao {
    protected CinemaHallDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction =null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinema hall to db: " + cinemaHall,e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class,id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from CinemaHall", CinemaHall.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls from db", e);
        }
    }
}
