package mate.academy.dao.impl;

import java.util.List;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao() {
        this.factory = HibernateUtil.getSessionFactory();
    }

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T persistEntity(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create entity " + entity + " on DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected <T> T getEntity(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.find(clazz, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find entity of class " + clazz.getName()
                    + " by id " + id, e);
        }
    }

    protected <T> List<T> getAllEntity(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all entities of type "
                    + clazz.getSimpleName(), e);
        }
    }
}
