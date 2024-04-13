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
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie spiderMan = new Movie("Spider Man");
        spiderMan.setDescription("Film about Spider Man");
        movieService.add(fastAndFurious);
        movieService.add(spiderMan);
        System.out.println("Movie with id 1:");
        System.out.println(movieService.get(1L));
        System.out.println("All movies:");
        System.out.println(movieService.getAll());

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall2d = new CinemaHall(20, "Regular 2d cinema hall");
        CinemaHall cinemaHall3d = new CinemaHall(15, "3D cinema hall");
        cinemaHallService.add(cinemaHall2d);
        cinemaHallService.add(cinemaHall3d);
        System.out.println("Cinema hall with id 1:");
        System.out.println(cinemaHallService.get(1L));
        System.out.println("All cinema halls:");
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession2d = new MovieSession(fastAndFurious, cinemaHall2d,
                LocalDateTime.of(2024, 04, 13, 15, 30));
        MovieSession movieSession3d = new MovieSession(spiderMan, cinemaHall3d,
                LocalDateTime.of(2024, 04, 26, 5, 25));
        movieSessionService.add(movieSession2d);
        movieSessionService.add(movieSession3d);
        System.out.println("Movie session with id 1:");
        System.out.println(movieSessionService.get(1L));
        System.out.println("All movie sessions with film with id 1 on April 13:");
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.of(2024, 04,
                13)));
    }
}
