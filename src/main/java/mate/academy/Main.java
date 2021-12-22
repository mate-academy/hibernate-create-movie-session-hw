package mate.academy;

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

        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall bigHall = new CinemaHall(1000, "Big and nice cinema hall");
        cinemaHallService.add(bigHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        CinemaHall smallHall = new CinemaHall(100, "Small one");
        cinemaHallService.add(smallHall);
        System.out.println(cinemaHallService.get(smallHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        LocalDateTime now = LocalDateTime.now();
        MovieSession firstSession = new MovieSession(fastAndFurious, bigHall, now);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        MovieSession secondSession = new MovieSession(fastAndFurious, smallHall, now.plusDays(1));
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), now.toLocalDate())
                .forEach(System.out::println);
    }
}
