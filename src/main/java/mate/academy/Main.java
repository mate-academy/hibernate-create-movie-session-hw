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
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        Movie interstellar = new Movie("Interstellar");
        Movie alien = new Movie("Alien");
        movieService.add(interstellar);
        movieService.add(alien);
        List<Movie> movies = movieService.getAll();
        movies.forEach(System.out::println);
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall1 = new CinemaHall(100);
        CinemaHall cinemaHall2 = new CinemaHall(200);
        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession interstellarSession = new MovieSession();
        interstellarSession.setMovie(interstellar);
        interstellarSession.setCinemaHall(cinemaHall1);
        interstellarSession.setShowTime(
                LocalDateTime.of(2022, 9, 2, 20, 00));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        movieSessionService.add(interstellarSession);
        MovieSession alienSession = new MovieSession();
        interstellarSession.setMovie(alien);
        interstellarSession.setCinemaHall(cinemaHall2);
        interstellarSession.setShowTime(
                LocalDateTime.of(2022, 9, 2, 21, 20));
        movieSessionService.add(interstellarSession);
        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(1L,
                        LocalDate.of(2022, 9, 2));
        availableSessions.forEach(System.out::println);
    }
}
