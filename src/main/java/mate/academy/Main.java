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
        Movie firstMovie = new Movie();
        firstMovie.setTitle("The Matrix");
        Movie secondMovie = new Movie();
        secondMovie.setTitle("Inception");

        movieService.add(firstMovie);
        movieService.add(secondMovie);
        System.out.println("All movies: " + movieService.getAll());
        System.out.println("Movie with ID 1: " + movieService.get(1L));

        // Test CinemaHallService
        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("IMAX Hall");

        CinemaHall secondHall = new CinemaHall();
        secondHall.setCapacity(50);
        secondHall.setDescription("Standard Hall");

        cinemaHallService.add(firstHall);
        cinemaHallService.add(secondHall);
        System.out.println("All cinema halls: " + cinemaHallService.getAll());
        System.out.println("Cinema hall with ID 1: " + cinemaHallService.get(1L));

        // Test MovieSessionService
        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(firstMovie);
        firstMovieSession.setCinemaHall(firstHall);
        firstMovieSession.setShowTime(LocalDateTime.of(2024, 12, 8, 14, 0));

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(secondMovie);
        secondMovieSession.setCinemaHall(secondHall);
        secondMovieSession.setShowTime(LocalDateTime.of(2024, 12, 8, 20, 0));

        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);

        LocalDate testDate = LocalDate.of(2024, 12, 8);
        System.out.println("Available sessions for 'The Matrix' on " + testDate + ": "
                + movieSessionService.findAvailableSessions(firstMovie.getId(), testDate));
    }
}
