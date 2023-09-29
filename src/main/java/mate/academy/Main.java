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

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious",
                "An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie inception = new Movie("Inception",
                "Film that will make your brain work!");
        movieService.add(inception);
        Movie babylon = new Movie("Babylon",
                "Movie about how movies were made in previous century");
        movieService.add(babylon);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall usualHall = new CinemaHall(100, "Usual-size hall");
        cinemaHallService.add(usualHall);

        CinemaHall premierHall = new CinemaHall(250, "Hall for premiers");
        cinemaHallService.add(premierHall);

        System.out.println(cinemaHallService.get(2L));
        System.out.println(cinemaHallService.getAll());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession firstMovieSession = new MovieSession(movieService.get(1L),
                cinemaHallService.get(1L), LocalDateTime.parse("2023-10-01T19:30"));
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession(movieService.get(2L),
                cinemaHallService.get(1L), LocalDateTime.parse("2023-10-03T16:00"));
        movieSessionService.add(secondMovieSession);

        MovieSession thirdMovieSession = new MovieSession(movieService.get(3L),
                cinemaHallService.get(2L), LocalDateTime.parse("2023-10-05T12:30"));
        movieSessionService.add(thirdMovieSession);

        System.out.println(movieSessionService.get(3L));
        System.out.println(movieSessionService.findAvailableSessions(
                1L, LocalDate.parse("2023-10-01")));
    }
}
