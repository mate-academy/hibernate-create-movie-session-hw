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
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Movie.class);
            configuration.addAnnotatedClass(CinemaHall.class);
            configuration.addAnnotatedClass(MovieSession.class);
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Error creating SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
