package mate.academy.dao.impl;

import org.hibernate.SessionFactory;

public class AbstractDao {
    protected final SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
