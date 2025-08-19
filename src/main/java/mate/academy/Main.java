package mate.academy;

import java.time.LocalDate;
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

    public static void main(String[] args) {
        // ========== Movie ==========
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie inception = new Movie("Inception");
        inception.setDescription("Mind-bending thriller by Christopher Nolan");
        movieService.add(inception);

        Movie matrix = new Movie("The Matrix");
        matrix.setDescription("Sci-fi classic about the Matrix world");
        movieService.add(matrix);

        System.out.println("All movies: " + movieService.getAll());

        // ========== CinemaHall ==========
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall1 = new CinemaHall("Big IMAX Hall", 250);
        cinemaHallService.add(hall1);

        CinemaHall hall2 = new CinemaHall("Cozy Small Hall", 80);
        cinemaHallService.add(hall2);

        System.out.println("Get hall1: " + cinemaHallService.get(hall1.getId()));
        System.out.println("All halls: " + cinemaHallService.getAll());

        // ========== MovieSession ==========
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession session1 = new MovieSession(
                inception, hall1, LocalDateTime.now().plusDays(1).withHour(18).withMinute(30)
        );
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession(
                matrix, hall2, LocalDateTime.now().plusDays(1).withHour(20).withMinute(0)
        );
        movieSessionService.add(session2);

        System.out.println("Get session1: " + movieSessionService.get(session1.getId()));

        List<MovieSession> availableTomorrow =
                movieSessionService.findAvailableSessions(inception.getId(),
                        LocalDate.now().plusDays(1));
        System.out.println("Available sessions for Inception tomorrow: " + availableTomorrow);
    }
}
