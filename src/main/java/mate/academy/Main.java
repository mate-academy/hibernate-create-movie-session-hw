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
    private static final LocalDate UNAVAILABLE_DATE = LocalDate.parse("2023-06-05");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription(
                "An action film about street racing, heists, and spies");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall vipHall = new CinemaHall(50, "awesome hall");
        cinemaHallService.add(vipHall);
        System.out.println(cinemaHallService.get(vipHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        MovieSession todaySession =
                new MovieSession(fastAndFurious, vipHall, LocalDateTime.now());
        movieSessionService.add(todaySession);
        System.out.println(
                movieSessionService.get(todaySession.getId()));
        System.out.println(
                movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        UNAVAILABLE_DATE));
        System.out.println(
                movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now()));
    }
}
