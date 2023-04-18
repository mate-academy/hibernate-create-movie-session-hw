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
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("\nTest MovieService:");
        System.out.println("Movie from db: " + movieService.get(fastAndFurious.getId()));
        System.out.println("All movies from db: ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("\n");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("IMAX");
        cinemaHall.setCapacity(200);
        cinemaHallService.add(cinemaHall);
        System.out.println("\nTest CinemaHallService:");
        System.out.println("CinemaHall from db: " + cinemaHallService.get(cinemaHall.getId()));
        System.out.println("All CinemaHall from db: ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("\n");

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setShowTime(
                LocalDateTime.of(2022, 2, 2,
                        12, 0, 0));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setShowTime(
                LocalDateTime.of(2022, 2, 2,
                        18, 0, 0));
        MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setShowTime(
                LocalDateTime.of(2022, 2, 3,
                        12, 0, 0));
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        System.out.println("\nTest MovieSessionService:");
        System.out.println("MovieSession from db: "
                + movieSessionService.get(movieSession1.getId()));
        LocalDate localDate = LocalDate.of(2022, 2, 2);
        System.out.println("All movies by " + localDate);
        System.out.println("Movie: " + fastAndFurious);
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), localDate)
                .forEach(System.out::println);
    }
}
