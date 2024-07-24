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

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall childrenHall = new CinemaHall();
        childrenHall.setCapacity(50);
        childrenHall.setDescription("Hall for children");
        cinemaHallService.add(childrenHall);
        System.out.println(cinemaHallService.get(childrenHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setCinemaHall(childrenHall);
        morningSession.setMovie(fastAndFurious);
        morningSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningSession);
        System.out.println(movieSessionService.get(morningSession.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(morningSession.getId(), LocalDate.now()));
    }
}
