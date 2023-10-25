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
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        System.out.println("\n\n============MOVIE===========");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("\n\n============CinemaHall===========");
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(25);
        cinemaHall.setDescription("welcome to the hall 1");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.get(1L));
        movieSession.setCinemaHall(cinemaHallService.get(2L));
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);

        System.out.println(movieSessionService.get(1L));

        System.out.println("\n\n=============MOVIESESSION================");
        for (MovieSession movieSession1: movieSessionService
                .findAvailableSessions(1L, LocalDate.now())) {
            System.out.println(movieSession1);
        }
    }
}
