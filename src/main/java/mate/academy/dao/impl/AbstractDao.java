package mate.academy.dao.impl;

import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

public abstract class AbstractDao {
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
}
