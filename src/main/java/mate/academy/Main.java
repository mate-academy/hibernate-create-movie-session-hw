package mate.academy;

import java.time.LocalDate;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final String PACKAGE_NAME = "mate.academy";
    private static final Injector injector = Injector.getInstance(PACKAGE_NAME);

    public static void main(String[] args) {

        var movieService = (MovieService) injector.getInstance(MovieService.class);
        var fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        var addedMovie = movieService.add(fastAndFurious);
        System.out.println("Added movie: " + movieService.get(addedMovie.getId()));
        movieService.getAll().forEach(System.out::println);

        var cinemaHallService =
                (CinemaHallService)injector.getInstance(CinemaHallService.class);
        var cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        cinemaHall.setDescription("Main Hall");
        var addedCinemaHall = cinemaHallService.add(cinemaHall);
        System.out.println("added cinema hall:"
                + cinemaHallService.get(addedCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        var movieSessionService =
                (MovieSessionService)injector.getInstance(MovieSessionService.class);
        var movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHallList(List.of(cinemaHall));
        movieSession.setLocalDate(LocalDate.now());
        var addedMovieSession = movieSessionService.add(movieSession);
        System.out.println("Added movie session: "
                + movieSessionService.get(addedMovieSession.getId()));
        movieSessionService.findAvailableSessions(addedMovie.getId(), LocalDate.now());
    }
}
