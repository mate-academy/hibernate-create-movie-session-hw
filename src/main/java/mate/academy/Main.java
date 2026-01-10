package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHallNumber5 = new CinemaHall(95);
        cinemaHallNumber5.setDescription("A large hall with comfortable chairs, "
                + "a high-quality screen and pleasant lighting.");
        cinemaHallService.add(cinemaHallNumber5);
        System.out.println(cinemaHallService.get(cinemaHallNumber5.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        MovieSession movieSessionNumber10 = new MovieSession(fastAndFurious);
        movieSessionNumber10.setCinemaHall(cinemaHallNumber5);
        movieSessionNumber10.setShowTime(LocalDateTime.of(2026, Month.JANUARY,
                11, 17, 30));
        movieSessionService.add(movieSessionNumber10);
        System.out.println(movieSessionService.get(movieSessionNumber10.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2026, Month.JANUARY, 11)).forEach(System.out::println);
    }
}
