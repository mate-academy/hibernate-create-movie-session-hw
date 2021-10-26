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

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinemaHall " + cinemaHall, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> getCinemaHallWithMovieSession = session
                    .createQuery("from CinemaHall c where c.id = :id", CinemaHall.class);
            getCinemaHallWithMovieSession.setParameter("id", id);
            return Optional.ofNullable(getCinemaHallWithMovieSession.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a cinemaHall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM CinemaHall",CinemaHall.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get cinemaHall list ", e);
        }
    }
}
