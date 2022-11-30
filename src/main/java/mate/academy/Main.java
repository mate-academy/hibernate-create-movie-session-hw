package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        //create movie
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        //create cinema hall
        CinemaHall hall = new CinemaHall(200, "Great cinema hall");
        cinemaHallService.add(hall);

        //create movie session
        LocalDateTime sessionDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 15));
        MovieSession movieSession = new MovieSession(fastAndFurious, hall, sessionDate);
        movieSessionService.add(movieSession);

        //print all
        printAll(movieService, fastAndFurious, cinemaHallService, hall,
                movieSessionService, movieSession, sessionDate);
    }

    public static void printAll(MovieService movieService, Movie fastAndFurious,
                                CinemaHallService cinemaHallService, CinemaHall hall,
                                MovieSessionService movieSessionService, MovieSession movieSession,
                                LocalDateTime sessionDate) {
        System.out.println("Movie by id: " + movieService.get(fastAndFurious.getId()));
        System.out.println("getAll movies:");
        movieService.getAll().forEach(movie -> System.out.println("movie: " + movie));

        System.out.println("Cinema hall by id: " + cinemaHallService.get(hall.getId()));
        System.out.println("getAll cinema halls:");
        cinemaHallService.getAll()
                .forEach(cinemaHall -> System.out.println("cinema hall: " + cinemaHall));

        System.out.println("Movie Session by id: " + movieSessionService.get(movieSession.getId()));
        System.out.println("getAll movie sessions, movie: " + fastAndFurious.getTitle());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                sessionDate.toLocalDate())
                .forEach(session -> System.out.println("session: " + session));
    }
}
