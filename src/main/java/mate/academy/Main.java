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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie oppenheimer = new Movie("OPPENHEIMER");
        oppenheimer.setDescription("A movie about physicist.");

        Movie barbie = new Movie("Barbie");
        barbie.setDescription("A movie with Ryan Gosling.");

        movieService.add(oppenheimer);
        movieService.add(barbie);
        movieService.getAll().forEach(System.out::println);

        CinemaHall bigHall = new CinemaHall(125, "Damn, that quite big.");
        CinemaHall smallHall = new CinemaHall(60, "Pretty good size.");

        cinemaHallService.add(bigHall);
        cinemaHallService.add(smallHall);
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDate date = LocalDate.of(2023, 7, 23);
        LocalDateTime morning = LocalDateTime.of(date, LocalTime.of(10, 0));
        LocalDateTime evening = LocalDateTime.of(date, LocalTime.of(20, 0));
        MovieSession morningSession = new MovieSession(barbie, smallHall, morning);
        MovieSession eveningSession = new MovieSession(oppenheimer, bigHall, evening);

        movieSessionService.add(morningSession);
        movieSessionService.add(eveningSession);
        movieSessionService.findAvailableSessions(barbie.getId(), date)
                .forEach(System.out::println);
    }
}
