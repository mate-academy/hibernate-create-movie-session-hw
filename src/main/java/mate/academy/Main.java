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

    private static final String PACKAGE_NAME = "mate.academy";
    private static final Injector INJECTOR = Injector.getInstance(PACKAGE_NAME);
    private static final String TITLE = "Fast and Furious";
    private static final String DESCRIPTION_OF_MOVIE = "An action film about street racing, "
            + "heists, and spies.";
    private static final long MOVIE_ID_ONE = 1L;
    private static final int CAPACITY_ONE_HUNDRED = 100;

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
        cinemaHall.setCapacity(CAPACITY_ONE_HUNDRED);
        cinemaHall.setDescription(fastAndFurious.getDescription());
        cinemaHallService.add(cinemaHall);

        final MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(MOVIE_ID_ONE, LocalDate.now());

        movieSessionService.findAvailableSessions(MOVIE_ID_ONE, LocalDate.now());
    }
}
