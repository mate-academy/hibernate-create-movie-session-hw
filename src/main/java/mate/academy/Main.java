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

    public static void main(String[] args) {
        // Initialization of services
        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        final MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);

        // Test-data preparation
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(80);
        cinemaHall.setDescription("Comfortable and spacious cinema hall.");

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.of(2026, 2, 26, 15, 30));

        // MovieService operations
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // CinemaHallService operations
        cinemaHallService.add(cinemaHall);
        cinemaHallService.get(cinemaHall.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        // MovieSessionService operations
        movieSession.setMovie(fastAndFurious);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2026, 2, 26)));
    }
}
