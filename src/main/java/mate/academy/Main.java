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
    private static final String MAIN_PACKAGE_NAME = "mate.academy";
    private static final int SHOW_YEAR = 2025;
    private static final int SHOW_MONTH = 12;
    private static final int SHOW_DAY_OF_MONTH = 18;
    private static final int SHOW_HOUR = 22;
    private static final int SHOW_MINUTE = 0;

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(MAIN_PACKAGE_NAME);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(400);
        redHall.setDescription("The most brand new hall decorated in red");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession eveningSession = new MovieSession();
        eveningSession.setMovie(fastAndFurious);
        eveningSession.setCinemaHall(redHall);
        eveningSession.setShowTime(LocalDateTime
                .of(SHOW_YEAR, SHOW_MONTH, SHOW_DAY_OF_MONTH, SHOW_HOUR, SHOW_MINUTE));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(eveningSession);
        System.out.println(movieSessionService.get(eveningSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.of(SHOW_YEAR, SHOW_MONTH, SHOW_DAY_OF_MONTH)));
    }
}
