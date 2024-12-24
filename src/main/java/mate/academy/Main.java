package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    public static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    public static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        CinemaHall cinemaHall = new CinemaHall(10, "cinemaHall");
        cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession(fastAndFurious, cinemaHall,
                LocalDateTime.of(2024, 10, 10, 10, 10));
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                movieSession.getShowTime().toLocalDate()));
    }
}

