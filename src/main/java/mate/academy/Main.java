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
        Movie lordOfTheRings = new Movie("LOTR", "Best u ever see!.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(lordOfTheRings);
        System.out.println(movieService.get(lordOfTheRings.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall(200, "Kievskaya Rus");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession(lordOfTheRings,
                cinemaHall, LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(movieSession.getId());
        movieSessionService.findAvailableSessions(lordOfTheRings.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
