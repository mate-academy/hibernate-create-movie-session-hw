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
    private static final String PACKAGE_NAME = "mate.academy";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE_NAME);
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);

        CinemaHall firstHall = new CinemaHall(50);
        firstHall.setDescription("Hall for 3d movies");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        LocalDate sessionDate = LocalDate.of(2024, 1, 22);
        LocalTime sessionTime = LocalTime.of(16, 30);
        MovieSession thridMovieSession = new MovieSession(fastAndFurious,
                firstHall, LocalDateTime.of(sessionDate, sessionTime));
        movieSessionService.add(thridMovieSession);
        System.out.println(movieSessionService.get(thridMovieSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), sessionDate));
    }
}
