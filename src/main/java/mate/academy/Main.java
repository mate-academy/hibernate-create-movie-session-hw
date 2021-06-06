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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final LocalDate DATE = LocalDate.of(2021, 06, 06);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious",
                        "An action film about street racing, heists, and spies...");
        Movie matrix = new Movie("Matrix", "It depicts a dystopian future in which "
                    + "humanity is unknowingly trapped inside a simulated reality, the Matrix...");

        System.out.println("--------------------Adding movies to DB: ");
        System.out.println(movieService.add(fastAndFurious));
        System.out.println(movieService.add(matrix));
        System.out.println("--------------------List of movies after calling method getAll(): ");
        movieService.getAll().forEach(System.out::println);
        System.out.println("--------------------Movie after calling method get() by movie id: ");
        System.out.println(movieService.get(fastAndFurious.getId()));

        CinemaHall redHall = new CinemaHall(150, "2D hall");
        CinemaHall blueHall = new CinemaHall(200, "3D hall");

        System.out.println("--------------------Adding cinema halls to DB: ");
        System.out.println(cinemaHallService.add(redHall));
        System.out.println(cinemaHallService.add(blueHall));
        System.out.println("--------------------List of cinema halls after "
                + "calling method getAll(): ");
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("--------------------Cinema hall after "
                + "calling method get() by cinema hall id: ");
        System.out.println(cinemaHallService.get(blueHall.getId()));

        MovieSession matrixDaySession = new MovieSession(matrix, redHall, LocalDateTime.of(
                DATE.getYear(), DATE.getMonthValue(), DATE.getDayOfMonth(), 14, 00));
        MovieSession matrixNightSession = new MovieSession(matrix, redHall, LocalDateTime.of(
                DATE.getYear(), DATE.getMonthValue(), DATE.getDayOfMonth(), 20, 00));

        System.out.println("--------------------Method add() was called: ");
        System.out.println(movieSessionService.add(matrixDaySession));
        System.out.println(movieSessionService.add(matrixNightSession));
        System.out.println("--------------------Movie session after "
                + "calling method get() by movie session id: ");
        System.out.println(movieSessionService.get(matrixNightSession.getId()));
        System.out.println("--------------------List of movie sessions after "
                + "calling method findAvailableSessions() by movie id and the date: ");
        movieSessionService.findAvailableSessions(matrix.getId(), DATE)
                .forEach(System.out::println);
    }
}
