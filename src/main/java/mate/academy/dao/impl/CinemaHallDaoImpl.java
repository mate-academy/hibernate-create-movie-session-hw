package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

@Dao
public class CinemaHallDaoImpl extends AbstractDao implements CinemaHallDao {
    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return super.add(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        return super.get(CinemaHall.class, id);
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CinemaHall", CinemaHall.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("The search for cinema halls was failed", e);
        }
    }
}
