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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall bestHall = new CinemaHall();
        bestHall.setCapacity(100);
        bestHall.setDescription("3D and big screen");
        cinemaHallService.add(bestHall);
        System.out.println("=== CINEMA HALL ===");
        System.out.println(cinemaHallService.get(bestHall.getId()));

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("=== FAST and FURIOUS ===");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("=== ALL MOVIE ===");
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(bestHall);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println("=== MOVIE SESSION ===");
        System.out.println(movieSessionService.get(movieSession.getId()));

        System.out.println("=== AVAILABLE SESSION ===");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
