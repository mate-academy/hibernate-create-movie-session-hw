package mate.academy.dao.impl;

import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

public abstract class AbstractDaoImpl {
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
}
