package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);
    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);

    public static void main(String[] args) {
        Movie movie = new Movie("Fast and Furious 9",
                "Awesome movie.");
        movieService.add(movie);
        System.out.println(movieService.get(movie.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall uncommonHall = new CinemaHall(100, "Big hall");
        cinemaHallService.add(uncommonHall);
        System.out.println(cinemaHallService.get(uncommonHall.getId()));
        CinemaHall commonHall = new CinemaHall(50, "Usual hall");
        cinemaHallService.add(commonHall);
        System.out.println(cinemaHallService.get(commonHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession lateSession = new MovieSession(movie, uncommonHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        movieSessionService.add(lateSession);
        System.out.println(movieSessionService.get(lateSession.getId()));
        MovieSession earlySession = new MovieSession(movie, commonHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        movieSessionService.add(earlySession);
        MovieSession tomorrowSession = new MovieSession(movie, uncommonHall,
                LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON));
        movieSessionService.add(tomorrowSession);
        movieSessionService.findAvailableSessions(movie.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
