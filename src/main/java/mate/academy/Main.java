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
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall smallCinemaHall = new CinemaHall(100, "small");
        CinemaHall largeCinemaHall = new CinemaHall(500, "large");
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(largeCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        MovieSession movieSession = new MovieSession(fastAndFurious, largeCinemaHall, date);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService
                .findAvailableSessions(movieSession.getId(), LocalDate.now()));
    }
}
