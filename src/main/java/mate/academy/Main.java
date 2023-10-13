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
        // MOVIE
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        // add:
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        // get:
        System.out.println(movieService.get(fastAndFurious.getId()));
        // getAll:
        movieService.getAll().forEach(System.out::println);

        // CINEMA HALL
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        // add:
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("A comfortable cinema hall");
        cinemaHallService.add(cinemaHall);
        // get:
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        // getAll:
        cinemaHallService.getAll().forEach(System.out::println);

        // MOVIE SESSION
        // add:
        MovieSession morningMovieSession = new MovieSession();
        morningMovieSession.setMovie(fastAndFurious);
        morningMovieSession.setCinemaHall(cinemaHall);
        LocalDateTime morningTime = LocalDateTime.of(2023, 11, 23, 9, 0, 0);
        morningMovieSession.setShowTime(morningTime);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningMovieSession);

        MovieSession eveningMovieSession = new MovieSession();
        eveningMovieSession.setMovie(fastAndFurious);
        eveningMovieSession.setCinemaHall(cinemaHall);
        LocalDateTime eveningTime = LocalDateTime.of(2023, 11, 23, 21, 0, 0);
        eveningMovieSession.setShowTime(eveningTime);
        movieSessionService.add(eveningMovieSession);
        // get:
        System.out.print(
                "MOVIE SESSION WITH ID 1:" + movieSessionService.get(morningMovieSession.getId()));
        // get by date:
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2023, 11, 23))
                .forEach(System.out::println);
    }
}
