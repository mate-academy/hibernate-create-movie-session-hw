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

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    public static final String GET_ALL_CINEMA_HALLS = "FROM CinemaHall";

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            throw new DataProcessingException("Can't insert cinemaHall " + cinemaHall, e);
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(GET_ALL_CINEMA_HALLS, CinemaHall.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving cinemaHalls", e);
        }
    }
}
