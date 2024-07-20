package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.ShoppingCart;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert shopping cart " + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ShoppingCart> shoppingCartQuery = session.createQuery(
                    "from ShoppingCart shc left join fetch shc.tickets"
                    + " where shc.id = :id", ShoppingCart.class);
            shoppingCartQuery.setParameter("id", id);
            return Optional.ofNullable(shoppingCartQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a shopping cart by id: " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ShoppingCart shc left join fetch shc.tickets",
                    ShoppingCart.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of shopping carts.", e);
        }
    }
}
