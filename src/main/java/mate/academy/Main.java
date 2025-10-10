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
        final MovieService movieService = (MovieService) injector
                .getInstance(MovieService.class);
        final Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing,"
                + " heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Movie added: " + movieService.get(fastAndFurious
                .getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");

        final CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final CinemaHall cinemaHall = new CinemaHall(50, "Large hall with 50 seats");
        cinemaHallService.add(cinemaHall);
        System.out.println("Cinema Hall added: " + cinemaHallService
                .get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("----------------------------------------------------------------");

        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        final MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.now().plusHours(2));
        movieSessionService.add(movieSession);
        System.out.println("Movie Session added: " + movieSessionService
                .get(movieSession.getId()));
        System.out.println("All available sessions for today:");
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.now())
                .forEach(System.out::println);
    }
}
