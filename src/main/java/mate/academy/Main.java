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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie slowAndPeaceful = new Movie("Slow and Peaceful");
        slowAndPeaceful.setDescription("An educational film about casual driving.");
        movieService.add(fastAndFurious);
        movieService.add(slowAndPeaceful);
        System.out.println(movieService.get(slowAndPeaceful.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall(125, "it's big ok?");
        CinemaHall smallHall = new CinemaHall(60, "slightly smaller than usual");
        cinemaHallService.add(bigHall);
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime morning = LocalDateTime.of(
                2023, 10, 1, 10, 00, 00);
        LocalDateTime evening = LocalDateTime.of(
                2023, 10, 1, 20, 00, 00);
        MovieSession morningSession = new MovieSession(slowAndPeaceful, smallHall, morning);
        MovieSession eveningSession = new MovieSession(fastAndFurious, bigHall, evening);
        movieSessionService.add(morningSession);
        movieSessionService.add(eveningSession);
        System.out.println(movieSessionService.get(eveningSession.getId()));
        movieSessionService.findAvailableSessions(slowAndPeaceful.getId(), LocalDate.of(
                2023, 10, 1)).forEach(System.out::println);
    }
}
