package mate.academy;

import java.time.LocalDate;
import mate.academy.lib.Injector;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSession = movieSessionService.get(5L);
        System.out.println(movieSession);

        System.out.println(movieSessionService.findAvailableSessions(4L,
                LocalDate.of(2023, 1, 25)));
    }
}
