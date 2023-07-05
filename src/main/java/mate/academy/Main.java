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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall greenCinemaHall = new CinemaHall(40, "3d");
        cinemaHallService.add(greenCinemaHall);
        System.out.println(cinemaHallService.get(greenCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime sessionLocalDateTime
                = LocalDateTime.of(2023, 5, 5, 17, 0);
        MovieSession session
                = new MovieSession(fastAndFurious, greenCinemaHall, sessionLocalDateTime);
        movieSessionService.add(session);
        System.out.println(movieSessionService.get(session.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                sessionLocalDateTime.toLocalDate())
                .forEach(System.out::println);
    }
}
