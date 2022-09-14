package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        // Create movies
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie inception = new Movie("Inception");

        // Add, get and getAll movies
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(inception);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        // Create cinemaHalls
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(200);
        redHall.setDescription("Red hall");

        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(100);
        greenHall.setDescription("Green hall");

        // Add, get and getAll cinemaHalls
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(redHall);
        cinemaHallService.add(greenHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        // Create movieSessions
        MovieSession morningFastAndFurious = new MovieSession();
        morningFastAndFurious.setMovie(fastAndFurious);
        morningFastAndFurious.setCinemaHall(greenHall);
        morningFastAndFurious.setShowTime(
                LocalDateTime.of(2022, Month.OCTOBER, 1, 9, 15));

        MovieSession eveningFastAndFurious = new MovieSession();
        eveningFastAndFurious.setMovie(fastAndFurious);
        eveningFastAndFurious.setCinemaHall(redHall);
        eveningFastAndFurious.setShowTime(
                LocalDateTime.of(2022, Month.OCTOBER, 1, 20, 30));

        MovieSession previewInception = new MovieSession();
        previewInception.setMovie(inception);
        previewInception.setCinemaHall(greenHall);
        previewInception.setShowTime(
                LocalDateTime.of(2022, Month.OCTOBER, 1, 13, 00));

        MovieSession premiereInception = new MovieSession();
        premiereInception.setMovie(inception);
        premiereInception.setCinemaHall(greenHall);
        premiereInception.setShowTime(
                LocalDateTime.of(2022, Month.OCTOBER, 2, 19, 00));

        // Add, get and findAvailableSessions movieSessions
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningFastAndFurious);
        movieSessionService.add(eveningFastAndFurious);
        movieSessionService.add(premiereInception);
        movieSessionService.add(previewInception);
        System.out.println(movieSessionService.get(premiereInception.getId()));
        // Find_Ok
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2022, Month.OCTOBER, 1)).forEach(System.out::println);
        // Find_EmptyList
        System.out.println(movieSessionService.findAvailableSessions(inception.getId(),
                LocalDate.of(2022, Month.OCTOBER, 3)));
        // Find_NotOk_Exception
        System.out.println(movieSessionService.findAvailableSessions(5L,
                LocalDate.of(2022, Month.OCTOBER, 1)));
    }
}
