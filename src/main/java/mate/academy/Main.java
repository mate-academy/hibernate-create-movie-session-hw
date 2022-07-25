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
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall redHall = new CinemaHall(20,"red");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        LocalDateTime currentDateTime = LocalDateTime.now();
        MovieSession firstSession = new MovieSession(fastAndFurious, redHall, currentDateTime);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        System.out.println("available movie today");
        LocalDate today = LocalDate.now();
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), today)
                .forEach(System.out::println);
        System.out.println("available tomorrow");
        LocalDate tomorrow = today.plusDays(1);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), tomorrow)
                .forEach(System.out::println);
    }
}
