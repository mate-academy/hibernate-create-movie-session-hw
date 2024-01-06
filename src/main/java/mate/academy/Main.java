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
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        Movie movie = new Movie("Fast and Furious");
        movie.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        System.out.println(movieService.get(movie.getId()).toString());
        movieService.getAll().forEach(o -> System.out.println(o.toString()));

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Built in 1967");
        cinemaHall.setCapacity(400);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()).toString());
        cinemaHallService.getAll().forEach(o -> System.out.println(o.toString()));

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()).toString());
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.now()).toString());
    }
}
