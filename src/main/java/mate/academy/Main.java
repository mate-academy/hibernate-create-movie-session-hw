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

        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);

        CinemaHall multiplexHall = new CinemaHall();
        multiplexHall.setCapacity(500);
        multiplexHall.setDescription("The biggest cinema hall in Chernihiv");
        cinemaHallService.add(multiplexHall);
        System.out.println(cinemaHallService.get(multiplexHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        MovieSession fastAndFuriousSession = new MovieSession(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(multiplexHall);
        fastAndFuriousSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(fastAndFuriousSession.getId()));
        movieSessionService.findAvailableSessions(
                1L, LocalDate.now()).forEach(System.out::println);
    }
}
