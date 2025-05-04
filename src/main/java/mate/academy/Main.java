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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall cinemaHall1 = new CinemaHall(20, "RedHall");
        CinemaHall cinemaHall2 = new CinemaHall(230, "BlueHall");

        cinemaHallService.add(cinemaHall1);
        cinemaHallService.add(cinemaHall2);

        MovieSession movieSession1 =
                new MovieSession(fastAndFurious,cinemaHall1, LocalDateTime.now());
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now()));

    }
}
