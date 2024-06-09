package mate.academy.dao.impl;

import mate.academy.lib.Dao;
import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

@Dao
public abstract class AbstractDao {
    protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
}
