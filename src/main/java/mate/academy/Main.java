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
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator = new Movie("Terminator", "Science fiction action film");
        movieService.add(terminator);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall multiplex = new CinemaHall(500, "multiplex");
        cinemaHallService.add(multiplex);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        MovieSession session = new MovieSession(fastAndFurious, multiplex,
                LocalDateTime.of(2024, 4, 7, 18, 0));
        movieSessionService.add(session);
        MovieSession session2 = new MovieSession(terminator, multiplex,
                LocalDateTime.of(2024, 4, 7, 14, 30));
        movieSessionService.add(session2);
        List<MovieSession> movieSessions = movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.of(2024, 4, 7));
        System.out.println("found sessions: " + movieSessions.toString());
    }
}
