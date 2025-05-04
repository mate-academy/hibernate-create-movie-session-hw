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
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall b3 = new CinemaHall(50);
        b3.setDescription("Big red hall on third floor");
        cinemaHallService.add(b3);
        System.out.println(cinemaHallService.get(b3.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession fastAndFuriousB3 = new MovieSession(fastAndFurious, b3, LocalDateTime.now());
        movieSessionService.add(fastAndFuriousB3);
        System.out.println(movieSessionService.get(fastAndFuriousB3.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(1997, 11, 16)).forEach(System.out::println);
    }
}
