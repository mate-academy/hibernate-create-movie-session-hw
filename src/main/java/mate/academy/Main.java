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
        Movie harryPotter = new Movie("Harry Potter");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        harryPotter.setDescription("Fantasy film about school of magic.");
        movieService.add(fastAndFurious);
        movieService.add(harryPotter);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall("First Hall");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime firstSessionTime = LocalDateTime.of(2023, 7, 30, 12, 00);
        LocalDateTime secondSessionTime = LocalDateTime.of(2023, 7, 30, 16, 00);
        MovieSession firstSession = new MovieSession(fastAndFurious, firstHall, firstSessionTime);
        MovieSession secondSession = new MovieSession(harryPotter, firstHall, secondSessionTime);
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.findAvailableSessions(firstHall.getId(),
                LocalDate.of(2023, 7, 30)).forEach(System.out::println);
    }
}
