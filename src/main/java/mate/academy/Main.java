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

    private static final MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);

    private static final CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);

    private static final MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        initializeMovies();
        initializeCinemaHalls();
        initializeMovieSessions();

        System.out.println("============================");
        movieService.getAll().forEach(System.out::println);
        System.out.println("============================");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("============================");
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 9, 12))
                .forEach(System.out::println);

    }

    private static void initializeMovies() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie movieTerminator = new Movie("The Terminator");
        movieTerminator.setDescription("Action with Arnold Schwarzenegger. 1984");
        movieService.add(movieTerminator);

        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
    }

    private static void initializeCinemaHalls() {
        CinemaHall yellow = new CinemaHall(20, "Yellow hall");
        cinemaHallService.add(yellow);

        CinemaHall green = new CinemaHall(80, "Green hall");
        cinemaHallService.add(green);
    }

    private static void initializeMovieSessions() {
        MovieSession msFastAndFurious1 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(1L), LocalDateTime.of(2021, 9, 12, 9, 0));
        movieSessionService.add(msFastAndFurious1);

        MovieSession msFastAndFurious2 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(1L), LocalDateTime.of(2021, 9, 12, 12, 0));
        movieSessionService.add(msFastAndFurious2);

        MovieSession msFastAndFurious3 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(2L), LocalDateTime.of(2021, 9, 12, 18, 0));
        movieSessionService.add(msFastAndFurious3);

        MovieSession msFastAndFurious4 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(1L), LocalDateTime.of(2021, 9, 14, 9, 0));
        movieSessionService.add(msFastAndFurious4);

        MovieSession msFastAndFurious5 = new MovieSession(movieService.get(1L),
                cinemaHallService.get(2L), LocalDateTime.of(2021, 9, 14, 9, 0));
        movieSessionService.add(msFastAndFurious5);

        MovieSession msTerminator1 = new MovieSession(movieService.get(2L),
                cinemaHallService.get(2L), LocalDateTime.of(2021, 9, 12, 9, 0));
        movieSessionService.add(msTerminator1);

        MovieSession msTerminator2 = new MovieSession(movieService.get(2L),
                cinemaHallService.get(1L), LocalDateTime.of(2021, 9, 12, 18, 0));
        movieSessionService.add(msTerminator2);
    }
}
