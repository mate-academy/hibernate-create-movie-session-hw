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

        CinemaHall redHall = new CinemaHall(35, "Red hall");
        CinemaHall yellowHall = new CinemaHall(65, "Yellow hall");
        cinemaHallService.add(redHall);
        cinemaHallService.add(yellowHall);
        System.out.println(cinemaHallService.get(yellowHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        MovieSession firstSession = new MovieSession(fastAndFurious, yellowHall,
                LocalDateTime.of(2021, 12, 24, 12, 30, 00));
        MovieSession secondSession = new MovieSession(fastAndFurious, redHall,
                LocalDateTime.of(2021, 12, 24, 10, 15, 00));
        MovieSession thirdSession = new MovieSession(fastAndFurious, redHall,
                LocalDateTime.of(2021, 12, 23, 10, 15, 00));
        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        System.out.println(movieSessionService.get(secondSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2021, 12, 24))
                .forEach(System.out::println);
    }
}
