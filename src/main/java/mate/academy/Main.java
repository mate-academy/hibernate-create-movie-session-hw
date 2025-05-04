package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.dto.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallRed = new CinemaHall(100, "red hall");
        CinemaHall cinemaHallBlue = new CinemaHall(100000, "blue hall");
        cinemaHallService.add(cinemaHallRed);
        cinemaHallService.add(cinemaHallBlue);
        System.out.println(cinemaHallService.get(cinemaHallRed.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime localDateTime =
                LocalDateTime.of(2024, 4, 7, 21, 0);
        LocalDate localDate = LocalDate.of(2024, 4, 7);
        MovieSession fastBlueSession =
                new MovieSession(fastAndFurious, cinemaHallBlue, localDateTime);
        movieSessionService.add(fastBlueSession);
        System.out.println(movieSessionService.get(cinemaHallRed.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), localDate)
                .forEach(System.out::println);
    }
}
