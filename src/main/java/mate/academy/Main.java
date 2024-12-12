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
        final MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        final MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        Movie movie = new Movie();
        movie.setTitle("new movie");
        movie.setDescription("interesting movie");
        movieService.add(movie);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(15);
        cinemaHall.setDescription("medium cinema hall");
        cinemaHallService.add(cinemaHall);

        MovieSession firstSession = new MovieSession();
        firstSession.setShowTime(LocalDateTime
                .of(2024, 12, 12, 8, 30));
        firstSession.setMovie(movie);
        firstSession.setCinemaHall(cinemaHall);
        movieSessionService.add(firstSession);

        MovieSession secondSession = new MovieSession();
        secondSession.setShowTime(LocalDateTime
                .of(2024, 12, 13, 8, 30));
        secondSession.setMovie(movie);
        secondSession.setCinemaHall(cinemaHall);
        movieSessionService.add(secondSession);

        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());
        System.out.println(movieService.get(1L));
        System.out.println(movieService.getAll());
        System.out.println(movieSessionService
                .findAvailableSessions(1L,
                        LocalDate.of(2024, 12, 13)));
    }
}
