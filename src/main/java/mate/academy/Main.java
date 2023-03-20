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
    private static final Long FIRST_MOVIE = 1L;
    private static final Long SECOND_MOVIE = 2L;
    private static final LocalDate TEST_DATE = LocalDate.parse("2023-03-19");
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie interstellar = new Movie("Interstellar");
        interstellar.setDescription("A movie about star-travel and love");
        movieService.add(interstellar);
        System.out.println(movieService.get(FIRST_MOVIE));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall red = new CinemaHall(120, "red");
        cinemaHallService.add(red);
        CinemaHall blue = new CinemaHall(250, "blue");
        cinemaHallService.add(blue);
        System.out.println(cinemaHallService.get(SECOND_MOVIE));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession before =
                new MovieSession(fastAndFurious, red, LocalDateTime.parse("2023-03-18T10:00:00"));
        movieSessionService.add(before);
        MovieSession testDaySessionOne =
                new MovieSession(fastAndFurious, red, LocalDateTime.parse("2023-03-19T12:00:00"));
        movieSessionService.add(testDaySessionOne);
        MovieSession testDaySessionTwo =
                new MovieSession(interstellar, blue, LocalDateTime.parse("2023-03-19T18:00:00"));
        movieSessionService.add(testDaySessionTwo);
        MovieSession after =
                new MovieSession(interstellar, blue, LocalDateTime.parse("2023-03-20T15:00:00"));
        movieSessionService.add(after);
        movieSessionService.findAvailableSessions(SECOND_MOVIE, TEST_DATE)
                           .forEach(System.out::println);
        movieSessionService.findAvailableSessions(FIRST_MOVIE, TEST_DATE)
                           .forEach(System.out::println);
    }
}
