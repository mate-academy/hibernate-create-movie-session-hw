package mate.academy.dao.impl;

import org.hibernate.SessionFactory;

public class AbstractDao {
    protected SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
