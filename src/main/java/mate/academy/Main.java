package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Movie movie = new Movie("Inception");
            movie.setDescription("A mind-bending thriller about dreams and reality.");
            movieService.add(movie);

            System.out.println("Movie by ID:");
            System.out.println(movieService.get(movie.getId()));

            System.out.println("All movies:");
            movieService.getAll().forEach(System.out::println);

            CinemaHall cinemaHall1 = new CinemaHall(30, "Standard");
            CinemaHall cinemaHall2 = new CinemaHall(50, "IMAX");
            cinemaHallService.add(cinemaHall1);
            cinemaHallService.add(cinemaHall2);

            System.out.println("Cinema hall by ID:");
            System.out.println(cinemaHallService.get(cinemaHall1.getId()));

            System.out.println("All cinema halls:");
            cinemaHallService.getAll().forEach(System.out::println);

            MovieSession session1 = new MovieSession(movie, cinemaHall1,
                    LocalDateTime.of(2023, 7, 10, 18, 0));
            MovieSession session2 = new MovieSession(movie, cinemaHall2,
                    LocalDateTime.of(2023, 7, 11, 20, 30));
            movieSessionService.add(session1);
            movieSessionService.add(session2);

            System.out.println("Movie session by ID:");
            System.out.println(movieSessionService.get(session1.getId()));

            System.out.println("All movie sessions:");
            movieSessionService
                    .findAvailableSessions(movie.getId(), LocalDate.of(2023, 7, 10))
                    .forEach(System.out::println);
        }
    }
}
