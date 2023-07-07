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
    private static Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("Movie by id: ");
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println("All movies: ");
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallVip = new CinemaHall(30, "VIP");
        CinemaHall cinemaHall3D = new CinemaHall(50, "3D");
        cinemaHallService.add(cinemaHallVip);
        cinemaHallService.add(cinemaHall3D);
        System.out.println("Cinema hall by id: ");
        System.out.println(cinemaHallService.get(cinemaHallVip.getId()));
        System.out.println("All cinema halls: ");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session
                = new MovieSession(fastAndFurious,
                cinemaHallVip,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11,15)));
        MovieSession session2
                = new MovieSession(fastAndFurious,
                cinemaHallVip,
                LocalDateTime.of(2023, 6, 20, 14, 30));
        MovieSession session3
                = new MovieSession(fastAndFurious,
                cinemaHallVip,
                LocalDateTime.of(2023, 6, 21, 12, 30));
        MovieSession session4
                = new MovieSession(fastAndFurious,
                cinemaHall3D,
                LocalDateTime.of(LocalDate.now(),
                        LocalTime.of(15,30)));
        movieSessionService.add(session);
        movieSessionService.add(session2);
        movieSessionService.add(session3);
        movieSessionService.add(session4);
        System.out.println("Movie session by id: ");
        System.out.println(movieSessionService.get(session.getId()));
        System.out.println("All movie session: ");
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
