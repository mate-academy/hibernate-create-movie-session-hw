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
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        // Test MovieService
        Movie movie1 = new Movie();
        movie1.setTitle("The Matrix");
        Movie movie2 = new Movie();
        movie2.setTitle("Inception");

        movieService.add(movie1);
        movieService.add(movie2);
        System.out.println("All movies: " + movieService.getAll());
        System.out.println("Movie with ID 1: " + movieService.get(1L));

        // Test CinemaHallService
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("IMAX Hall");

        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(50);
        hall2.setDescription("Standard Hall");

        cinemaHallService.add(hall1);
        cinemaHallService.add(hall2);
        System.out.println("All cinema halls: " + cinemaHallService.getAll());
        System.out.println("Cinema hall with ID 1: " + cinemaHallService.get(1L));

        // Test MovieSessionService
        MovieSession session1 = new MovieSession();
        session1.setMovie(movie1);
        session1.setCinemaHall(hall1);
        session1.setShowTime(LocalDateTime.of(2024, 12, 8, 14, 0));

        MovieSession session2 = new MovieSession();
        session2.setMovie(movie2);
        session2.setCinemaHall(hall2);
        session2.setShowTime(LocalDateTime.of(2024, 12, 8, 20, 0));

        movieSessionService.add(session1);
        movieSessionService.add(session2);

        LocalDate testDate = LocalDate.of(2024, 12, 8);
        System.out.println("Available sessions for 'The Matrix' on " + testDate + ": "
                + movieSessionService.findAvailableSessions(movie1.getId(), testDate));
    }
}
