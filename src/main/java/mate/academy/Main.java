package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);

    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie terminatorOne = new Movie("Terminator", "Robots are bad");
        Movie terminatorTwo = new Movie("Terminator 2", "Robots are still bad");
        movieService.add(terminatorTwo);
        movieService.add(terminatorOne);
        System.out.println(movieService.get(terminatorOne.getId()));
        System.out.println(movieService.getAll());

        CinemaHall smallHall = new CinemaHall(60, "Hall for 60 people. 3D movie");
        CinemaHall bigHall = new CinemaHall(110, "Hall for 110 people. 2D movie");
        cinemaHallService.add(smallHall);
        cinemaHallService.add(bigHall);
        System.out.println(cinemaHallService.get(bigHall.getId()));
        System.out.println(cinemaHallService.getAll());

        MovieSession firstSession = new MovieSession(terminatorOne, smallHall,
                LocalDateTime.of(2023, Month.APRIL, 16, 10, 56));
        MovieSession secondSession = new MovieSession(terminatorTwo, smallHall,
                LocalDateTime.of(2023, Month.APRIL, 12, 15, 30));
        MovieSession thirdSession = new MovieSession(terminatorTwo, bigHall,
                LocalDateTime.of(2023, Month.APRIL, 16, 11, 50));
        MovieSession fourthSession = new MovieSession(terminatorOne, bigHall,
                LocalDateTime.of(2023, Month.APRIL, 12, 15, 20));

        movieSessionService.add(firstSession);
        movieSessionService.add(secondSession);
        movieSessionService.add(thirdSession);
        movieSessionService.add(fourthSession);
        System.out.println(movieService.get(secondSession.getId()));
        System.out.println(movieService.getAll());
        System.out.println(movieSessionService.findAvailableSessions(terminatorOne.getId(),
                LocalDate.of(2023, Month.APRIL, 16)));
    }
}
