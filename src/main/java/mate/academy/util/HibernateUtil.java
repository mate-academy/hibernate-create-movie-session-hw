package mate.academy.util;

import java.io.FileInputStream;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/db.properties"));

            Configuration configuration = new Configuration().configure();
            configuration.setProperty("hibernate.connection.url",
                    props.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username",
                    props.getProperty("db.username"));
            configuration.setProperty("hibernate.connection.password",
                    props.getProperty("db.password"));

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Can't build SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
