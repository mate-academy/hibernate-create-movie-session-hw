package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

public abstract class AbstractDao {
    protected <T> T add(T entity) {
        try {
            HibernateUtil.getSessionFactory().inTransaction(s -> s.persist(entity));
            return entity;
        } catch (Exception e) {
            throw new DataProcessingException(
                    "Can't add %s entity to DB".formatted(entity.getClass().getSimpleName()), e
            );
        }
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
