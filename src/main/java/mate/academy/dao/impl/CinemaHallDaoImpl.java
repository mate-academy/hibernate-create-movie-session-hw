package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save cinemaHall "
                    + cinemaHall.toString() + " to DB", e);
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
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = factory.openSession()) {
            Query<CinemaHall> getAllCommentsQuery
                    = session.createQuery("from CinemaHall");
            return getAllCommentsQuery.list();
        }
    }
}
