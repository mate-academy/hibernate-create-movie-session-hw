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
    static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        List<CinemaHall> cinemaHalls = List.of(
                new CinemaHall(20, "Small hall for your company"),
                new CinemaHall(100,
                        "IMAX 3D hall where you can enjoy fresh hi-tech movies"),
                new CinemaHall(100,
                        "Classic hall where we show most of our films")
        );
        cinemaHalls.forEach(cinemaHall -> {
            cinemaHallService.add(cinemaHall);
            System.out.println(cinemaHallService.get(cinemaHall.getId()));
        });
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        List<MovieSession> movieSessions = List.of(
                new MovieSession(fastAndFurious, cinemaHalls.get(1),
                        LocalDateTime.now().minusDays(1)),
                new MovieSession(fastAndFurious, cinemaHalls.get(2),
                        LocalDateTime.now()),
                new MovieSession(fastAndFurious, cinemaHalls.get(cinemaHalls.size() - 1),
                        LocalDateTime.now().plusDays(1))
        );
        movieSessions.forEach(movieSession -> {
            movieSessionService.add(movieSession);
            System.out.println(movieSessionService.get(movieSession.getId()));
        });
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(LocalDateTime.now()));

    }
}
