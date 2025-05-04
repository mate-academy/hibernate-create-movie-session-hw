package mate.academy;

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
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie batman = new Movie("Dark Knight");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        batman.setDescription("BATMAN");
        movieService.add(fastAndFurious);
        movieService.add(batman);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(batman.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(100);
        firstHall.setDescription("RED");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session28 = new MovieSession();
        session28.setMovie(fastAndFurious);
        session28.setCinemaHall(firstHall);
        LocalDateTime localDate28 = LocalDateTime.of(2023, 12, 28, 17, 0);
        session28.setShowTime(localDate28);
        movieSessionService.add(session28);

        MovieSession session25 = new MovieSession();
        session25.setMovie(batman);
        session25.setCinemaHall(firstHall);
        LocalDateTime localDate25 = LocalDateTime.of(2023, 12, 25, 19, 0);
        session25.setShowTime(localDate25);
        movieSessionService.add(session25);
        System.out.println(movieSessionService.get(session25.getId()));
        movieSessionService.findAvailableSessions(1L, localDate25.toLocalDate())
                .forEach(System.out::println);
    }
}
