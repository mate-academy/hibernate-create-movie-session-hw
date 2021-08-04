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
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie testMovie1 = new Movie("test movie 1");
        testMovie1.setDescription("1");
        movieService.add(testMovie1);
        Movie testMovie2 = new Movie("test movie 2");
        testMovie2.setDescription("2");
        movieService.add(testMovie2);
        movieService.getAll().forEach(System.out::println);

        CinemaHall testCinemaHall1 = new CinemaHall(27, "1");
        cinemaHallService.add(testCinemaHall1);
        CinemaHall testCinemaHall2 = new CinemaHall(5, "2");
        cinemaHallService.add(testCinemaHall2);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession testMovieSession1 = new MovieSession(testMovie1,
                testCinemaHall1, LocalDateTime.now());
        movieSessionService.add(testMovieSession1);
        MovieSession testMovieSession2 = new MovieSession(testMovie2,
                testCinemaHall2, LocalDateTime.now().plusDays(1));
        movieSessionService.add(testMovieSession2);

        System.out.println("Sessions today" + movieSessionService
                .findAvailableSessions(testMovie1.getId(), LocalDate.now()));
    }
}
