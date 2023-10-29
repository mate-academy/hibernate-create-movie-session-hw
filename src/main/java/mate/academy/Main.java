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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = movieService
                .add(new Movie("Fast and Furious",
                        "An action film about street racing, heists, and spies."));
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService
                = (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall firstCinemaHall
                = cinemaHallService.add(new CinemaHall(200, "awesome IMAX hall"));
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        MovieSession firstMovieSession
                = movieSessionService.add(new MovieSession(fastAndFurious, firstCinemaHall,
                LocalDateTime.of(2023, 10, 4, 18, 30)));
        movieSessionService.add(new MovieSession(fastAndFurious, firstCinemaHall,
                LocalDateTime.of(2023, 10, 3, 18, 30)));
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.of(2023, 10, 4)).forEach(System.out::println);
    }
}
