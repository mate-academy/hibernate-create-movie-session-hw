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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinemaHall: " + cinemaHall, e);
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
            Query<CinemaHall> cinemaHallGetAllQuery = session.createQuery(
                    "FROM CinemaHall c WHERE c.id = :id", CinemaHall.class);
            cinemaHallGetAllQuery.setParameter("id", id);
            return Optional.ofNullable(cinemaHallGetAllQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall on id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> cinemaHallGetAllQuery = session.createQuery(
                    "FROM CinemaHall", CinemaHall.class);
            return cinemaHallGetAllQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all cinema hall", e);
        }
    }
}
