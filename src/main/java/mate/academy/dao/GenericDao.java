package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

public interface GenericDao<T> {
    SessionFactory factory = HibernateUtil.getSessionFactory();

    T add(T t);

    Optional<T> get(Long id);

    List<T> getAll();
}
