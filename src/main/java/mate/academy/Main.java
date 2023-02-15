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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static MovieService movieService;
    private static CinemaHallService cinemaHallService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);

        CinemaHall deluxe = new CinemaHall(100, "Deluxe");
        cinemaHallService.add(deluxe);
        System.out.println(cinemaHallService.get(deluxe.getId()));
        movieService.getAll().forEach(System.out::println);

        movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        LocalDateTime morningShowTime
                = LocalDateTime.of(2023, 02, 15, 11, 30);
        LocalDateTime eveningShowTime
                = LocalDateTime.of(2023, 02, 14, 18, 0);
        MovieSession morningSession = new MovieSession(fastAndFurious, deluxe, morningShowTime);
        MovieSession eveningSession = new MovieSession(fastAndFurious, deluxe, eveningShowTime);
        movieSessionService.add(morningSession);
        movieSessionService.add(eveningSession);
        System.out.println(movieSessionService.get(morningSession.getId()));
        System.out.println(movieSessionService.get(eveningSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
