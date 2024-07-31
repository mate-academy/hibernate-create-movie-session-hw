package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // Add a movie
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Added movie: " + movieService.get(fastAndFurious.getId()));

        // Add a cinema hall
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(20);
        hall1.setDescription("Main Hall");
        cinemaHallService.add(hall1);
        System.out.println("Added cinema hall: " + cinemaHallService.get(hall1.getId()));

        // Add a movie session
        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(hall1);
        session1.setShowTime(LocalDate.of(2024, 7, 31).atStartOfDay()); // Adjusted to LocalDate
        movieSessionService.add(session1);
        System.out.println("Added movie session: " + movieSessionService.get(session1.getId()));

        // Print all movies
        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        // Print all cinema halls
        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // Print all movie sessions for a given date
        System.out.println("All movie sessions for 2024-12-31:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2024, 7, 31))
                .forEach(System.out::println);
    }
}
