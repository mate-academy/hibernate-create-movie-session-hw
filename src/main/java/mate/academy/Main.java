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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        CinemaHall mainHall = new CinemaHall(300, "Main hall");
        cinemaHallService.add(mainHall);

        MovieSession movieSession1 =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.of(2022, 12, 5, 19, 00));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.of(2022, 12, 6, 19, 00));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 =
                new MovieSession(fastAndFurious, mainHall, LocalDateTime.of(2022, 12, 7, 19, 00));
        movieSessionService.add(movieSession3);

        List<MovieSession> movieSessions =
                movieSessionService.findAvailableSessions(1L, LocalDate.now());

        System.out.println(movieSessions);

    }
}
