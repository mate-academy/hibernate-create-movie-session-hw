package mate.academy.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public interface GenericDao<T> {
    default T add(T entity) {
        Session session = null;
        Transaction transaction = null;
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
            throw new DataProcessingException("Can't insert " + entity + " in DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    Optional<T> get(Long id);

    default Optional<T> get(Class<T> entityClazz, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            T entity = session.get(entityClazz, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get " + entityClazz.getSimpleName() + " by id=" + id + " from DB", e);
        }
    }

    default List<T> getList(String getEntitiesQuery, Map<String, Object> queryParameters,
                            Class<T> entityClazz) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery(getEntitiesQuery, entityClazz);
            for (Map.Entry<String, Object> entry : queryParameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get " + entityClazz.getSimpleName() + "s from DB", e);
        }
    }
}
