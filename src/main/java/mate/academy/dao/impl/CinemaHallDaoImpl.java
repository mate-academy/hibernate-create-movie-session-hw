package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;

@Dao
public class CinemaHallDaoImpl extends AbstractDao implements CinemaHallDao {
    public CinemaHallDaoImpl() {
        super(HibernateUtil.getSessionFactory());
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return super.create(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return super.get(CinemaHall.class, id);
    }

    @Override
    public List<CinemaHall> getAll() {
        return super.getAll(CinemaHall.class, "CinemaHall");
    }
}
