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
        // Test Movie service
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        System.out.println("=== Testing Movie Service ===");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Added movie: " + movieService.get(fastAndFurious.getId()));

        Movie avengers = new Movie("Avengers");
        avengers.setDescription("Superhero team saves the world");
        movieService.add(avengers);

        System.out.println("\nAll movies:");
        movieService.getAll().forEach(System.out::println);

        // Test CinemaHall service
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        System.out.println("\n=== Testing CinemaHall Service ===");
        CinemaHall blueHall = new CinemaHall(100, "Blue Hall");
        cinemaHallService.add(blueHall);
        System.out.println("Added cinema hall: "
                + cinemaHallService.get(blueHall.getId()));

        CinemaHall redHall = new CinemaHall(150, "Red Hall");
        cinemaHallService.add(redHall);

        System.out.println("\nAll cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // Test MovieSession service
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        System.out.println("\n=== Testing MovieSession Service ===");
        LocalDateTime today10am = LocalDate.now().atTime(10, 0);
        LocalDateTime today14pm = LocalDate.now().atTime(14, 0);
        LocalDateTime today18pm = LocalDate.now().atTime(18, 0);
        LocalDateTime tomorrow10am = LocalDate.now().plusDays(1).atTime(10, 0);

        MovieSession session1 = new MovieSession(
                fastAndFurious, blueHall, today10am);
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession(
                fastAndFurious, redHall, today14pm);
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession(
                fastAndFurious, blueHall, today18pm);
        movieSessionService.add(session3);

        MovieSession session4 = new MovieSession(
                avengers, redHall, tomorrow10am);
        movieSessionService.add(session4);

        System.out.println("Added session: "
                + movieSessionService.get(session1.getId()));

        // Test findAvailableSessions
        System.out.println("\n=== Available Sessions for Fast and Furious today ===");
        movieSessionService.findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);

        System.out.println("\n=== Available Sessions for Avengers tomorrow ===");
        movieSessionService.findAvailableSessions(
                        avengers.getId(), LocalDate.now().plusDays(1))
                .forEach(System.out::println);
    }
}
