package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final LocalDateTime TODAY = LocalDateTime
            .of(2022, Month.JUNE, 1, 23, 59, 59)
            .atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneOffset.systemDefault())
            .toLocalDateTime();
    private static final LocalDateTime TOMORROW = LocalDateTime
            .of(2022, Month.JUNE, 2, 0, 0, 0)
            .atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneOffset.systemDefault())
            .toLocalDateTime();
    private static final LocalDate VISIT_DATE = LocalDate.of(2022, Month.JUNE, 1);

    public static void main(String[] args) {
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie darkKnight = new Movie("Dark Knight");
        darkKnight.setDescription("An action film about superhero called Batman.");
        movieService.add(darkKnight);
        System.out.println(movieService.get(darkKnight.getId()));
        movieService.getAll().forEach(e -> System.out.println("All movies: " + e));

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall jadeHall = new CinemaHall(30, "Jade 4DX Hall");
        CinemaHall rubyHall = new CinemaHall(30, "Ruby 4DX Hall");
        cinemaHallService.add(jadeHall);
        cinemaHallService.add(rubyHall);
        System.out.println(cinemaHallService.get(jadeHall.getId()));
        System.out.println(cinemaHallService.get(rubyHall.getId()));
        cinemaHallService.getAll().forEach(e -> System.out.println("All halls: " + e));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession jadeMovieSession = new MovieSession(darkKnight, jadeHall, TODAY);
        MovieSession rubyMovieSession = new MovieSession(fastAndFurious, rubyHall, TOMORROW);
        movieSessionService.add(jadeMovieSession);
        movieSessionService.add(rubyMovieSession);
        System.out.println(movieSessionService.get(jadeMovieSession.getId()));
        System.out.println(movieSessionService.get(rubyMovieSession.getId()));
        movieSessionService.findAvailableSessions(darkKnight.getId(), VISIT_DATE)
                .forEach(e -> System.out.println("All sessions for movie on the date: " + e));
    }
}
