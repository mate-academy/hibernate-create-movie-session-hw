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
    private static Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.getAll());
        CinemaHall imax = new CinemaHall(100,"Hall with IMAX technology");
        cinemaHallService.add(imax);
        CinemaHall twoDimensional = new CinemaHall(60,"Hall with 2D technology");
        cinemaHallService.add(twoDimensional);
        System.out.println(cinemaHallService.getAll());
        MovieSession firstSession
                = new MovieSession(movieService.get(1L),cinemaHallService.get(1L),
                LocalDateTime.of(2022,05,10,12,30));
        movieSessionService.add(firstSession);
        MovieSession secondSession
                = new MovieSession(movieService.get(1L),cinemaHallService.get(2L),
                LocalDateTime.of(2022,05,11,18,15));
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2022, 05, 10)));
    }
}
