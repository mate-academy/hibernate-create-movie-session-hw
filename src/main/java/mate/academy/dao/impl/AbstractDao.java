package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

public abstract class AbstractDao {
    protected <T> T add(T entity) {
        HibernateUtil.getSessionFactory().inTransaction(session -> {
            try {
                session.persist(entity);
            } catch (RuntimeException e) {
                throw new DataProcessingException(
                        "Can't add %s entity to DB".formatted(entity.getClass().getSimpleName()), e
                );
            }
        });
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
