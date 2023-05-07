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
        // Adding movie
        Movie movie = new Movie();
        movie.setTitle("Mavka. Lisova Pisnya.");
        movie.setDescription("Best Ukrainian cartoon!");
        movieService.add(movie);
        // Adding hall
        CinemaHall hall = new CinemaHall();
        hall.setDescription("Our first hall!");
        hall.setCapacity(100);
        cinemaHallService.add(hall);
        System.out.println(cinemaHallService.get(hall.getId()));
        // Adding past session
        MovieSession pastSession = new MovieSession();
        pastSession.setCinemaHall(hall);
        pastSession.setMovie(movie);
        pastSession.setShowTime(LocalDateTime.of(2023, 4, 17, 18, 30));
        movieSessionService.add(pastSession);
        System.out.println(movieSessionService.get(pastSession.getId()));
        // Adding future session
        MovieSession futureSession = new MovieSession();
        futureSession.setCinemaHall(hall);
        futureSession.setMovie(movie);
        futureSession.setShowTime(LocalDateTime.of(2023, 4, 18, 18, 30));
        movieSessionService.add(futureSession);
        System.out.println(movieSessionService.get(futureSession.getId()));
        // Showing available sessions
        System.out.println(movieSessionService
                .findAvailableSessions(movie.getId(),
                        LocalDate.of(2023, 4, 18)));
    }
}
