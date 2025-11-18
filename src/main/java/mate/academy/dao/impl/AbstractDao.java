package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao<T> {

    private final Class<T> entityType;

    public AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    protected void persistEntity(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected Optional<T> findEntity(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(entityType, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get an entity by id: " + id, e);
        }
    }

    protected List<T> findAllEntities() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery("from " + entityType.getTypeName(), entityType);
            return query.getResultList();
        } catch (Exception e) {
            String message = "Can't get a list of entities: " + entityType.getTypeName();
            throw new DataProcessingException(message, e);
        }
    }
}
