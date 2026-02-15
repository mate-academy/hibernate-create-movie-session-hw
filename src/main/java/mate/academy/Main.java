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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        // Create and add movies
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie inception = new Movie();
        inception.setTitle("Inception");
        inception.setDescription("A thief who steals corporate secrets through"
                + " dream-sharing technology.");
        movieService.add(inception);

        // Get movie by id
        System.out.println("Get movie by id: " + movieService.get(fastAndFurious.getId()));

        // Get all movies
        System.out.println("\nAll movies:");
        movieService.getAll().forEach(System.out::println);

        // Create and add cinema halls
        CinemaHall hallOne = new CinemaHall();
        hallOne.setCapacity(100);
        hallOne.setDescription("IMAX Hall");
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);
        cinemaHallService.add(hallOne);

        CinemaHall hallTwo = new CinemaHall();
        hallTwo.setCapacity(50);
        hallTwo.setDescription("VIP Hall");
        cinemaHallService.add(hallTwo);

        // Get cinema hall by id
        System.out.println("\nGet cinema hall by id: " + cinemaHallService.get(hallOne.getId()));

        // Get all cinema halls
        System.out.println("\nAll cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        // Create and add movie sessions
        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(hallOne);
        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        session1.setShowTime(LocalDateTime.of(2026, 2, 15, 18, 0));
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(fastAndFurious);
        session2.setCinemaHall(hallTwo);
        session2.setShowTime(LocalDateTime.of(2026, 2, 15, 20, 30));
        movieSessionService.add(session2);

        MovieSession session3 = new MovieSession();
        session3.setMovie(inception);
        session3.setCinemaHall(hallOne);
        session3.setShowTime(LocalDateTime.of(2026, 2, 15, 21, 0));
        movieSessionService.add(session3);

        MovieSession session4 = new MovieSession();
        session4.setMovie(fastAndFurious);
        session4.setCinemaHall(hallOne);
        session4.setShowTime(LocalDateTime.of(2026, 2, 16, 19, 0));
        movieSessionService.add(session4);

        // Get movie session by id
        System.out.println("\nGet movie session by id: "
                + movieSessionService.get(session1.getId()));

        // Find available sessions for a specific movie and date
        System.out.println("\nAvailable sessions for 'Fast and Furious' on 2026-02-15:");
        java.util.List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.of(2026, 2, 15)
        );
        availableSessions.forEach(System.out::println);

        System.out.println("\nAvailable sessions for 'Fast and Furious' on 2026-02-16:");
        List<MovieSession> sessionsNextDay = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(),
                LocalDate.of(2026, 2, 16)
        );
        sessionsNextDay.forEach(System.out::println);
    }
}
