package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        Movie constantine = new Movie("Constantine",
                "As a suicide survivor, demon hunter John Constantine (Keanu Reeves) has "
                        + "literally been to hell and back -- and he knows that when he dies, "
                        + "he's got a one-way ticket to Satan's realm unless he can earn enough "
                        + "goodwill to climb God's stairway to heaven.");
        Movie bridgeToTerabithia = new Movie("Bridge to Terabithia",
                "Bridge to Terabithia is a faithful adaptation of a beloved children's "
                        + "novel and a powerful portrayal of love, loss, and imagination through "
                        + "children's eyes.");
        movieService.add(fastAndFurious);
        movieService.add(constantine);
        movieService.add(bridgeToTerabithia);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall imax = new CinemaHall(300, "IMAX");
        CinemaHall cinetech2D = new CinemaHall(310, "CINETECH");
        CinemaHall cinetech3D = new CinemaHall(290, "CINETECH+");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance((CinemaHallService.class));
        cinemaHallService.add(imax);
        cinemaHallService.add(cinetech2D);
        cinemaHallService.add(cinetech3D);
        System.out.println(cinemaHallService.get(imax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        List<MovieSession> sessions = List.of(
                new MovieSession(fastAndFurious, imax, LocalDateTime.now().plusDays(1L)),
                new MovieSession(bridgeToTerabithia, cinetech2D, LocalDateTime.now().plusDays(1L)),
                new MovieSession(constantine, cinetech3D, LocalDateTime.now().plusDays(1L)));
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        sessions.forEach(movieSessionService::add);
        sessions.forEach(e -> System.out.println(movieService.get(e.getId())));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now().plusDays(1L)).forEach(System.out::println);
    }
}
