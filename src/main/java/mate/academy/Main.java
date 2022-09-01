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
    private static final String INJECTOR_INSTANCE = "mate.academy";
    private static final Injector injector = Injector.getInstance(INJECTOR_INSTANCE);
    private static final LocalDateTime SHOW_TIME =
            LocalDateTime.of(2022, 9, 1, 17, 40);
    private static final LocalDateTime SHOW_PRIME_TIME =
            LocalDateTime.of(2022, 9, 2, 19, 20);
    private static final LocalDate SESSION_DATE = LocalDate.of(2022, 9, 1);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie matrix = new Movie("Matrix");
        matrix.setDescription("Wake up, Neo...");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(matrix);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(matrix.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall hallMiramax = new CinemaHall();
        hallMiramax.setCapacity(144);
        hallMiramax.setDescription("3D super vision technology");
        CinemaHall hallDolbyAtmos = new CinemaHall();
        hallDolbyAtmos.setCapacity(206);
        hallDolbyAtmos.setDescription("Biggest screen");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hallMiramax);
        cinemaHallService.add(hallDolbyAtmos);
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession fastAndFuriousSession = new MovieSession();
        fastAndFuriousSession.setMovie(fastAndFurious);
        fastAndFuriousSession.setCinemaHall(hallMiramax);
        fastAndFuriousSession.setShowTime(SHOW_PRIME_TIME);
        MovieSession matrixSession = new MovieSession();
        matrixSession.setMovie(matrix);
        matrixSession.setCinemaHall(hallDolbyAtmos);
        matrixSession.setShowTime(SHOW_TIME);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fastAndFuriousSession);
        movieSessionService.add(matrixSession);
        System.out.println("There are " + System.lineSeparator() + fastAndFuriousSession
                + System.lineSeparator() + "and" + System.lineSeparator() + matrixSession
                + System.lineSeparator() + "movie sessions" + System.lineSeparator()
                + "Available sessions on " + SESSION_DATE + ":");
        System.out.println(movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), SESSION_DATE));
        System.out.println(movieSessionService
                .findAvailableSessions(matrix.getId(), SESSION_DATE));
    }
}
