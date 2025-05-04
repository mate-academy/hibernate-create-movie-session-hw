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

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall h1 = new CinemaHall();
        h1.setDescription("H1");
        h1.setCapacity(100);
        cinemaHallService.add(h1);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession =
                new MovieSession(fastAndFurious, h1,
                        LocalDateTime.of(2022, 1, 1, 10, 0));
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2022, 1, 1))
                .forEach(System.out::println);
    }
}
