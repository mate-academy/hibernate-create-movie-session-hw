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
        Movie shawshank = new Movie("The Shawshank Redemption", "9.3/10");
        Movie godFather = new Movie("The Godfather", "9.2/10");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(shawshank);
        movieService.add(godFather);

        CinemaHall bigCinemaHall = new CinemaHall(350, "Big hall");
        CinemaHall smallCinemaHall = new CinemaHall(80, "Small hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(bigCinemaHall);
        cinemaHallService.add(smallCinemaHall);

        MovieSession earlyMovieSession = new MovieSession(godFather, smallCinemaHall,
                LocalDateTime.of(2024, 12, 12, 12, 0));
        MovieSession lateMovieSession = new MovieSession(shawshank, bigCinemaHall,
                LocalDateTime.of(2024, 12, 12, 20, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(earlyMovieSession);
        movieSessionService.add(lateMovieSession);
        System.out.println(movieSessionService.findAvailableSessions(
                2L, LocalDate.of(2024, 12, 12)));
    }
}
