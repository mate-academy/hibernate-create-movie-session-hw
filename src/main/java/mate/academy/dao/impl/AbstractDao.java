package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    public AbstractDao(SessionFactory factory) {
        this.factory = factory;
    }

    protected <T> T create(T entity) {
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
            throw new DataProcessingException("Can't add a "
                    + entity.getClass().getSimpleName() + " = [" + entity + "]", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected <T> Optional<T> get(Class<T> entityClass, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a "
                    + entityClass.getSimpleName() + " by id = [" + id + "]", e);
        }
    }

    protected <T> List<T> getAll(Class<T> entityClass, String className) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + className, entityClass).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all " + className + "s", e);
        }
    }
}
