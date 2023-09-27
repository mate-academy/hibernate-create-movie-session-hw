package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected <T> T add(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    "Can't add %s entity to DB".formatted(entity.getClass().getSimpleName()), e
            );
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected <T> Optional<T> get(Class<T> type, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(type, id));
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't get a %s by id: %d".formatted(type.getSimpleName(), id), e
            );
        }
    }
}
