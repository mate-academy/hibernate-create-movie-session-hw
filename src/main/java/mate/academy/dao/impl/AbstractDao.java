package mate.academy.dao.impl;

import mate.academy.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao() {
        this.factory = HibernateUtil.getSessionFactory();
    }
}
