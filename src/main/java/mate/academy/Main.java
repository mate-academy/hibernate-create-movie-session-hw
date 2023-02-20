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
    private static final Injector injector = Injector.getInstance("mate");

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
        CinemaHall redCinemaHall = new CinemaHall();
        redCinemaHall.setCapacity(100);
        redCinemaHall.setDescription("Red hall");
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(redCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession =
                new MovieSession(LocalDateTime.of(2023, 2,19,7,30));
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(redCinemaHall);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2023, 2, 19))
                .forEach(System.out::println);
    }
}
