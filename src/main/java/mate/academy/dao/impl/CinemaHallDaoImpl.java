package mate.academy.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(cinemaHall);
            entityTransaction.commit();
            return cinemaHall;
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't add cinema hall " + cinemaHall, e);
        }
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            CinemaHall cinemaHall = entityManager.find(CinemaHall.class, id);
            entityTransaction.commit();
            return Optional.ofNullable(cinemaHall);
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't find cinema hall by id " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        EntityTransaction entityTransaction = null;

        try (EntityManager entityManager = HibernateUtil
                .getSessionFactory()
                .openSession()) {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            TypedQuery<CinemaHall> cinemaHalls = entityManager
                    .createQuery("from CinemaHall ", CinemaHall.class);
            entityTransaction.commit();
            return cinemaHalls.getResultList();
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }

            throw new DataProcessingException("Can't get all cinema halls", e);
        }
    }
}
