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
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("common cinema hall");
        cinemaHallService.add(firstHall);
        System.out.println("Added cinema hall: " + cinemaHallService.get(firstHall.getId()));
        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(firstHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println("Added session: " + movieSessionService.get(movieSession.getId()));
        System.out.println("Available sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
