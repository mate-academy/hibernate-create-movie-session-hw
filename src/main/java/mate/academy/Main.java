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

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall fastAndFuriousInLargeHall = new CinemaHall(300, "Large hall");
        cinemaHallService.add(fastAndFuriousInLargeHall);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession firstSessionFastAndFurious = new MovieSession(
                fastAndFurious, fastAndFuriousInLargeHall,
                LocalDateTime.now());
        movieSessionService.add(firstSessionFastAndFurious);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        cinemaHallService.get(fastAndFuriousInLargeHall.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println(movieSessionService.get(firstSessionFastAndFurious.getId()));
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
