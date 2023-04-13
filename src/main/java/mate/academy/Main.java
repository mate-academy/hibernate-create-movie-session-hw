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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie movie1 = movieService.add(new Movie("The Wizard of Oz", "very awesome movie"));
        Movie movie2 = movieService.add(new Movie("Casablanca", "just awesomve movie"));
        System.out.println(movieService.get(1L));
        System.out.println(movieService.getAll());

        CinemaHall cinemaHall1 = cinemaHallService.add(new CinemaHall(200, "hall for 200 people"));
        CinemaHall cinemaHall2 = cinemaHallService.add(new CinemaHall(250, "hall for 250 people"));
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession1 = movieSessionService.add(new MovieSession(movie1,
                LocalDateTime.of(2023, Month.MARCH, 22, 15, 00), cinemaHall1));
        MovieSession movieSession2 = movieSessionService.add(new MovieSession(movie1,
                LocalDateTime.of(2023, Month.MARCH, 22, 17, 00), cinemaHall1));
        MovieSession movieSession3 = movieSessionService.add(new MovieSession(movie2,
                LocalDateTime.of(2023, Month.MARCH, 23, 15, 00), cinemaHall2));
        MovieSession movieSession4 = movieSessionService.add(new MovieSession(movie1,
                LocalDateTime.of(2023, Month.MARCH, 23, 15, 00), cinemaHall1));
        System.out.println(movieSessionService.get(1L));
        System.out.println(movieSessionService.getAll());
        System.out.println(movieSessionService.findAvailableSessions(movie1.getId(),
                LocalDate.of(2023, Month.MARCH, 22)));
    }
}
