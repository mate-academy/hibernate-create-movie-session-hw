package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall blueHall = new CinemaHall(45, "Blue hall");
        cinemaHallService.add(blueHall);
        System.out.println(movieService.get(blueHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionEighthOfAugust =
                new MovieSession(fastAndFurious, blueHall,
                        LocalDateTime.of(
                                LocalDate.of(2023, Month.AUGUST, 8),
                                LocalTime.of(12, 30))
                );
        MovieSession movieSessionNinthOfAugust =
                new MovieSession(fastAndFurious, blueHall,
                        LocalDateTime.of(
                                LocalDate.of(2023, Month.AUGUST, 9),
                                LocalTime.of(12, 30))
                );
        movieSessionService.add(movieSessionEighthOfAugust);
        movieSessionService.add(movieSessionNinthOfAugust);
        System.out.println(movieSessionService.get(movieSessionEighthOfAugust.getId()));
        System.out.println(movieSessionService.get(movieSessionNinthOfAugust.getId()));
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()).forEach(System.out::println
        );
    }
}
