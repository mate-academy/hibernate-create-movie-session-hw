package mate.academy.util;

import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(CinemaHall.class)
                    .addAnnotatedClass(MovieSession.class)
                    .addAnnotatedClass(Movie.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Error creating SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
