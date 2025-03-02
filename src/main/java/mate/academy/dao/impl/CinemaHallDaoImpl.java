package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl extends AbstractDao implements mate.academy.dao.CinemaHallDao {
    public static final String ERROR_DURING_CREATING_MOVIE =
            "Error during creating movie -> %s";
    public static final String ERROR_DURING_RETRIEVING_CINEMA_HALL_WITH_ID =
            "Error during retrieving cinema hall with id -> %d";
    public static final String ERROR_DURING_RETRIEVING_ALL_CINEMA_HALLS =
            "Error during retrieving all cinema halls.";
    public static final String SELECT_ALL_HALLS = "FROM CinemaHall c";

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    ERROR_DURING_CREATING_MOVIE.formatted(cinemaHall), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_CINEMA_HALL_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_HALLS, CinemaHall.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(ERROR_DURING_RETRIEVING_ALL_CINEMA_HALLS, e);
        }
    }
}
