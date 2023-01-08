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
    private static Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        final MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall superHall = new CinemaHall(240, "Cool hall");
        cinemaHallService.add(superHall);
        System.out.println(movieService.get(superHall.getId()));

        MovieSession fastAndFuriousSession =
                new MovieSession(LocalDateTime.now(), superHall, fastAndFurious);
        movieSessionService.add(fastAndFuriousSession);
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDateTime.now()));
    }
}
