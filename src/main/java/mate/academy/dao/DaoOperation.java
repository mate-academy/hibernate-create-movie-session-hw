package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DaoOperation<T> {
    private String entityName;

    public DaoOperation(String entityName) {
        this.entityName = entityName;
    }

    public T add(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert " + entityName + ": " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Optional<T> get(Long id, Class<T> cls) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(cls, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a " + entityName + " by id: " + id, e);
        }
    }

    public List<T> getAll(String query, Class<T> cls) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> allEntitiesQuery = session.createQuery(query, cls);
            return allEntitiesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list of all " + entityName + "s.", e);
        }
    }
}
