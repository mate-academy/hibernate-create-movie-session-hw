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
    private static final MovieService movie =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHall =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSession =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movie.add(fastAndFurious);
        System.out.println(movie.get(fastAndFurious.getId()));
        movie.getAll().forEach(System.out::println);

        CinemaHall hall1 = new CinemaHall(13,"we have screen");
        cinemaHall.add(hall1);
        System.out.println(cinemaHall.get(hall1.getId()));
        cinemaHall.getAll().forEach(System.out::println);

        MovieSession session = new MovieSession(fastAndFurious, hall1, LocalDateTime.now());
        movieSession.add(session);
        System.out.println(movieSession.get(session.getId()));
        movieSession.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);

    }
}
