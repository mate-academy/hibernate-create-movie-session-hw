package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.OrderDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Order;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert order " + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> orderQuery = session.createQuery("from Order o LEFT JOIN FETCH o.tickets"
                    + " where o.id = :id", Order.class);
            orderQuery.setParameter("id", id);
            return Optional.ofNullable(orderQuery.getSingleResult());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a order by id: " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Order o left join fetch o.tickets",
                    Order.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a list of orders.", e);
        }
    }
}
