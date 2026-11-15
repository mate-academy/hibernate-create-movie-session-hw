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

        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // Add movie
        Movie movie = new Movie("Interstellar");
        movieService.add(movie);

        // Add cinema hall
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("IMAX");

        cinemaHallService.add(cinemaHall);

        // Add movie session
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now().plusDays(1));

        movieSessionService.add(movieSession);

        // Get movie
        System.out.println(movieService.get(movie.getId()));

        // Get all movies
        System.out.println(movieService.getAll());

        // Get cinema hall
        System.out.println(cinemaHallService.get(cinemaHall.getId()));

        // Get all cinema halls
        System.out.println(cinemaHallService.getAll());

        // Get movie session
        System.out.println(movieSessionService.get(movieSession.getId()));

        // Find available sessions
        System.out.println(movieSessionService.findAvailableSessions(
                movie.getId(),
                LocalDate.now().plusDays(1)
        ));
    }
}
