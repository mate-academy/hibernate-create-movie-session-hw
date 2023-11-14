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
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    public static final String GET_ALL_CINEMA_HALLS_QUERY =
            "from CinemaHall";
    private static final String CANT_ADD_CINEMA_HALLS_EXCEPTION_MESSAGE =
            "Can't add CinemaHalls: ";
    private static final String CANT_GET_ALL_CINEMA_HALLS_EXCEPTION_MESSAGE =
            "Can't get list of CinemaHalls";
    private static final String CANT_GET_CINEMA_HALL_EXCEPTION_MESSAGE =
            "Can't get CinemaHall by id: ";

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(CANT_ADD_CINEMA_HALLS_EXCEPTION_MESSAGE
                    + cinemaHall, e);
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
            throw new DataProcessingException(CANT_GET_CINEMA_HALL_EXCEPTION_MESSAGE + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            Query<CinemaHall> getAllCinemaHallsQuery = session.createQuery(
                    GET_ALL_CINEMA_HALLS_QUERY, CinemaHall.class);
            return getAllCinemaHallsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(CANT_GET_ALL_CINEMA_HALLS_EXCEPTION_MESSAGE, e);
        }
    }
}
