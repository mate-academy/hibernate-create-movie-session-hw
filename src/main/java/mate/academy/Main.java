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

    private static final String PACKAGE_NAME = "mate.academy";
    private static final Injector INJECTOR = Injector.getInstance(PACKAGE_NAME);
    private static final String TITLE = "Fast and Furious";
    private static final String DESCRIPTION_OF_MOVIE = "An action film about street racing, "
            + "heists, and spies.";
    private static final String AVAILABLE_MOVIE_SESSIONS_MSG = "Available movie sessions ";

    public static void main(String[] args) {
        MovieService movieService = (MovieService)
                INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie(TITLE);
        fastAndFurious.setDescription(DESCRIPTION_OF_MOVIE);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService)
                INJECTOR.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription(fastAndFurious.getDescription());
        cinemaHallService.add(cinemaHall);

        System.out.println(cinemaHallService.getAll());

        final MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        List<MovieSession> availableSessions =
                movieSessionService.findAvailableSessions(1L, LocalDate.now());

        System.out.println(availableSessions + AVAILABLE_MOVIE_SESSIONS_MSG);
    }
}
