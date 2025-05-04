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
        Movie killBill = new Movie("Kill Bill", "We need to kill our old friend Bill");
        movieService.add(killBill);
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(
                CinemaHallService.class);

        CinemaHall smallCinemaHall = new CinemaHall(50, "Small Cinema Hall");
        CinemaHall bigCinemaHall = new CinemaHall(200, "Big Cinema Hall");
        cinemaHallService.add(smallCinemaHall);
        cinemaHallService.add(bigCinemaHall);

        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(
                MovieSessionService.class);
        MovieSession movieSession = new MovieSession(fastAndFurious,
                        bigCinemaHall, LocalDateTime.of(
                                2024, 4, 11, 18, 30));
        movieSessionService.add(movieSession);

        MovieSession movieSession2 = new MovieSession(killBill, smallCinemaHall,
                LocalDateTime.of(2024, 4, 10, 19, 20));
        movieSessionService.add(movieSession2);

        List<MovieSession> movieSessions = movieSessionService.findAvailableSessions(
                killBill.getId(), LocalDate.of(2024, 4, 10));
        System.out.println("found sessions: " + movieSessions.toString());
    }
}
