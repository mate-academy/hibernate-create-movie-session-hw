package mate.academy.dao.impl;

import java.util.List;
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
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall: " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public CinemaHall get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> query = session.createQuery("from CinemaHall ch "
                    + "left join fetch ch.movieSessions "
                    + "where ch.id = :id", CinemaHall.class);
            query.setParameter("id", id);
            return query.getSingleResult();
            //            CinemaHall cinemaHall = session.get(CinemaHall.class, id);
            //            Hibernate.initialize(cinemaHall);
            //            return cinemaHall;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get the cinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM CinemaHall ch left join fetch ch.movieSessions";
            Query<CinemaHall> query = session.createQuery(hql, CinemaHall.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error getting all cinemaHalls from DB", e);
        }
    }
}
