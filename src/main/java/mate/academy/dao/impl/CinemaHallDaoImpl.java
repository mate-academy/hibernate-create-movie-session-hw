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
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            cinemaHall.setId((Long) session.getIdentifier(cinemaHall));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinema hall. "
                    + "Cinema hall: " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinema hall by id. Id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from CinemaHall ", CinemaHall.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}
