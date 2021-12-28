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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie movie = new Movie("Matrix");
        movie.setDescription("Action");
        movieService.add(movie);
        Movie movie1 = new Movie("Dogma");
        movie1.setDescription("cool");
        movieService.add(movie1);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(10);
        cinemaHall.setDescription("Max Comfort");
        cinemaHallService.add(cinemaHall);
        MovieSession movieSession1 = new MovieSession(
                movie, cinemaHall, LocalDateTime.of(2022, 03, 04, 17, 0));
        MovieSession movieSession2 = new MovieSession(
                movie1, cinemaHall, LocalDateTime.of(2022, 04, 25, 12, 0));
        MovieSession movieSession3 = new MovieSession(
                movie1, cinemaHall, LocalDateTime.of(2022, 06, 26, 18, 0));
        MovieSession movieSession4 = new MovieSession(
                movie, cinemaHall, LocalDateTime.of(2022, 03, 27, 14, 0));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        movieSessionService.add(movieSession4);
        System.out.println(movieService.get(movie.getId()));
        movieService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(
                movie1.getId(), LocalDate.of(2022, 04, 25)).forEach(System.out::println);
        movieSessionService.findAvailableSessions(
                movie.getId(), LocalDate.of(2022, 03, 04)).forEach(System.out::println);
    }
}
