package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.GenericDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    @Override
    public T add(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert entity " + t, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public abstract Optional<T> get(Long id);

    @Override
    public abstract List<T> getAll();
}
