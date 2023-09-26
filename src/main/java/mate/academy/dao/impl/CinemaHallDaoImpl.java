package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        try {
            HibernateUtil.getFactory().inTransaction(session -> session.persist(cinemaHall));
            return cinemaHall;
        } catch (Exception e) {
            throw new DataProcessingException("Can't add cinemaHall " + cinemaHall, e);
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        try {
            return HibernateUtil.getFactory()
                    .fromSession(session -> Optional.ofNullable(session.get(CinemaHall.class, id)));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get CinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try {
            return HibernateUtil.getFactory()
                    .fromSession(session -> session.createQuery("FROM CinemaHall", CinemaHall.class)
                    .getResultList());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get List of CinemaHalls", e);
        }
    }
}
