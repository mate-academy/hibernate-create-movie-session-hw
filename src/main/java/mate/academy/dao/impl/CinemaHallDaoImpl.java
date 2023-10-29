package mate.academy.dao.impl;

import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final String EXCEPTION_GET = "Can't get cinemaHall by id ";
    private static final String EXCEPTION_ADD = "Can't add cinemaHall ";
    private static final String EXCEPTION_GET_ALL = "Can't get all cinemaHalls";

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(EXCEPTION_GET + id, e);
        }
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(EXCEPTION_ADD + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query getAllCinemaHall
                    = session.createQuery("from CinemaHall", CinemaHall.class);
            return getAllCinemaHall.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(EXCEPTION_GET_ALL, e);
        }
    }
}
