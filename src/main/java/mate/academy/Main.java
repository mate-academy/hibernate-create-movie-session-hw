package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        movieOptions();
        cinemaHallOptions();
        movieSessionOptions();
    }

    private static void movieOptions() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println();
    }

    private static void cinemaHallOptions() {
        CinemaHall littleCinemaHall = new CinemaHall(50, "Little cinema hall");
        cinemaHallService.add(littleCinemaHall);
        CinemaHall mediumCinemaHall = new CinemaHall(150, "Medium cinema hall");
        cinemaHallService.add(mediumCinemaHall);
        CinemaHall bigCinemaHall = new CinemaHall(300, "Big cinema hall");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(littleCinemaHall.getId()));
        System.out.println(cinemaHallService.get(mediumCinemaHall.getId()));
        System.out.println(cinemaHallService.get(bigCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());
        System.out.println();
    }

    private static void movieSessionOptions() {
        MovieSession movieSession1 =
                new MovieSession(movieService.get(1L),
                        cinemaHallService.get(1L),
                        LocalDateTime.of(LocalDate.of(2023,06,28),LocalTime.of(15,30)));
        MovieSession movieSession2 =
                new MovieSession(movieService.get(1L),
                        cinemaHallService.get(2L),
                        LocalDateTime.of(LocalDate.now(),LocalTime.of(9,30)));
        MovieSession movieSession3 =
                new MovieSession(movieService.get(1L),
                        cinemaHallService.get(3L),
                        LocalDateTime.of(LocalDate.now(),LocalTime.of(21,30)));
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println(movieSessionService.get(movieSession3.getId()));
        System.out.println("Available Session:");
        for (MovieSession availableSession : movieSessionService
                .findAvailableSessions(1L, LocalDate.now())) {
            System.out.println(availableSession);
        }
    }
}
