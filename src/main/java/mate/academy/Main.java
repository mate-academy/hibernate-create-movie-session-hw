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
        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Movie added: " + movieService.get(fastAndFurious.getId()));
        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall bigHall = new CinemaHall();
        bigHall.setCapacity(200);
        bigHall.setDescription("Big cinema hall with surround sound.");
        cinemaHallService.add(bigHall);
        System.out.println("CinemaHall added: " + cinemaHallService.get(bigHall.getId()));
        System.out.println("All cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(bigHall);
        movieSession.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(movieSession);
        System.out.println("MovieSession added: "
                + movieSessionService.get(movieSession.getId()));
        System.out.println("Available sessions:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now().plusDays(1))
                .forEach(System.out::println);
    }
}
