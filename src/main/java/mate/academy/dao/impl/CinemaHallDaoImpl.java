package mate.academy.dao.impl;

import static mate.academy.util.HqlQueries.GET_ALL_CINEMA_HALLS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

@Dao
public class CinemaHallDaoImpl extends AbstractDao<CinemaHall> implements CinemaHallDao {

    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return executeInsideTransaction(session -> {
            session.persist(cinemaHall);
            return cinemaHall;
        });
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(CinemaHall.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get a cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var cinemaHallList = session.createQuery(GET_ALL_CINEMA_HALLS, CinemaHall.class).list();
            return cinemaHallList != null ? cinemaHallList : new ArrayList<>();
        } catch (Exception e) {
            throw new DataProcessingException("Unable to get all cinema halls: ", e);
        }
    }
}
