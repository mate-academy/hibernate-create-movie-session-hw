package mate.academy;

import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall cinemaHall = new CinemaHall(200, "The biggest cinema hall in our cinema.");
        cinemaHallService.add(cinemaHall);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie movieFromDb = movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSessionToday = new MovieSession(movieFromDb, cinemaHall,
                LocalDateTime.now().minusHours(5));
        movieSessionService.add(movieSessionToday);

        MovieSession movieSessionYesterday = new MovieSession(movieFromDb, cinemaHall,
                LocalDateTime.now().minusDays(1));
        movieSessionService.add(movieSessionYesterday);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movieFromDb.getId(), LocalDateTime.now().toLocalDate());
        availableSessions.forEach(System.out::println);
    }
}
