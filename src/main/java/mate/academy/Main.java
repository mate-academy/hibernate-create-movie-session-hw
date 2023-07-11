package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        // Add movie
        System.out.println("---Adding movie---");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(fastAndFurious);

        // Add cinema hall
        System.out.println("\n---Adding cinema hall---");
        CinemaHall cinemaHall = new CinemaHall(20, "First hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHall);

        // Add movie session
        System.out.println("\n---Adding movie session---");
        MovieSession movieSession =
                new MovieSession(fastAndFurious, cinemaHall, LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieSession);

        // Get movie by id
        System.out.println("\n---Getting movie by id---");
        System.out.println(movieService.get(fastAndFurious.getId()));

        // Get cinema hall by id
        System.out.println("\n---Getting cinema hall by id---");
        System.out.println(movieService.get(cinemaHall.getId()));

        // Get movie session by id
        System.out.println("\n---Getting movie session by id---");
        System.out.println(movieService.get(movieSession.getId()));

        // Get all movies
        System.out.println("\n---Getting all movies---");
        movieService.getAll().forEach(System.out::println);

        // Get all cinema halls
        System.out.println("\n---Getting all cinema halls---");
        cinemaHallService.getAll().forEach(System.out::println);

        // Get all movie sessions by movie id and date
        System.out.println("\n---Getting all movie sessions by movie id and date---");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);;
    }
}
