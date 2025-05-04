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
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall", exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception exception) {
            throw new DataProcessingException("Can't get a CinemaHall with id: " + id, exception);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String getAllQuery = "from CinemaHall";
            Query<CinemaHall> query = session.createQuery(getAllQuery, CinemaHall.class);
            return query.getResultList();
        } catch (Exception exception) {
            throw new DataProcessingException("Failed to retrieve all CinemaHalls", exception);
        }
    }
}
