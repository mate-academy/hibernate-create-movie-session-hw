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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall simpleCinemaHall = new CinemaHall();
        simpleCinemaHall.setCapacity(50);
        simpleCinemaHall.setDescription("A simple cinema hall for people who love watching movies");

        CinemaHall cinemaHallWithAmenities = new CinemaHall();
        cinemaHallWithAmenities.setCapacity(200);
        cinemaHallWithAmenities.setDescription("A cinema hall with a lot"
                + " of amenities for our visitors");
        // AN ADDITIONAL MESSAGE
        System.out.println("I have created two instances of CinemaHall class");

        Movie crazyWeddingTwo = new Movie();
        crazyWeddingTwo.setTitle("Crazy Wedding 2");
        crazyWeddingTwo.setDescription("A story about the Ukrainian wedding");

        Movie homeAloneTwo = new Movie();
        homeAloneTwo.setTitle("Home Alone 2");
        homeAloneTwo.setDescription("A story about a boy who at Christmas"
                + " was alone at home without relatives");
        // AN ADDITIONAL MESSAGE
        System.out.println("I have created two instances of Movie class");

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(crazyWeddingTwo);
        firstMovieSession.setCinemaHall(simpleCinemaHall);
        firstMovieSession.setShowTime(LocalDateTime.of(2023, 4, 20, 20, 00, 00, 0));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(homeAloneTwo);
        secondMovieSession.setCinemaHall(cinemaHallWithAmenities);
        secondMovieSession.setShowTime(LocalDateTime.of(2023, 4, 21, 19, 00, 00, 0));
        // AN ADDITIONAL MESSAGE
        System.out.println("I have created two instances of MovieSession class");

        cinemaHallService.add(simpleCinemaHall);
        cinemaHallService.add(cinemaHallWithAmenities);
        movieService.add(crazyWeddingTwo);
        movieService.add(homeAloneTwo);
        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        // AN ADDITIONAL MESSAGE
        System.out.println("I have added all instances to the database");

        System.out.println(cinemaHallService.getAll());
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed all cinema halls from the database");
        System.out.println(movieService.getAll());
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed all movies from the database");
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2023, 4, 20)));
        System.out.println(movieSessionService
                .findAvailableSessions(2L, LocalDate.of(2023, 4, 21)));
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed all movie session for a particular day");

        System.out.println(cinemaHallService.get(1L));
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed a cinema hall by id 1");
        System.out.println(movieService.get(2L));
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed a movie by id 2");
        System.out.println(movieSessionService.get(1L));
        // AN ADDITIONAL MESSAGE
        System.out.println("I have printed a movie session by id 1");
    }
}
