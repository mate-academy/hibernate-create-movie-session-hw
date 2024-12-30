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

    private static final String CAN_T_GET_LIST_OF_CINEMA_HALL_MSG = "Can't get list of CinemaHall ";
    private static final String CAN_T_GET_CINEMA_HALL_BY_ID_MSG = "Can't get CinemaHall by Id ";
    private static final String CAN_T_ADD_CINEMA_HALL_MSG = "Can't add CinemaHall ";

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CAN_T_ADD_CINEMA_HALL_MSG + cinemaHall, e);
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
            CinemaHall cinemaHall = session.find(CinemaHall.class, id);
            return Optional.of(cinemaHall);
        } catch (RuntimeException e) {
            throw new DataProcessingException(CAN_T_GET_CINEMA_HALL_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> fromCinemaHall =
                    session.createQuery("FROM CinemaHall ", CinemaHall.class);
            return fromCinemaHall.getResultList();
        } catch (RuntimeException e) {
            throw new DataProcessingException(CAN_T_GET_LIST_OF_CINEMA_HALL_MSG, e);
        }
    }
}
