package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        // Test MovieService
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // Test CinemaHallService
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall imax = new CinemaHall(280, "IMAX Theater with 3D");
        cinemaHallService.add(imax);
        System.out.println(cinemaHallService.get(imax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        // Test MovieSessionService
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession morningSession = new MovieSession(
                fastAndFurious,
                imax,
                LocalDateTime.of(2023, 1, 20, 10, 0));
        movieSessionService.add(morningSession);
        System.out.println(movieSessionService.get(morningSession.getId()));

        // Find available sessions for today
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(),
                        LocalDate.of(2023, 1, 20));
        System.out.println("Available sessions for Fast and Furious on 2023-01-20:");
        availableSessions.forEach(System.out::println);
    }
}

