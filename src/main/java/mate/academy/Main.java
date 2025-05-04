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
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall(200, "IMAX 3D");
        CinemaHall secondCinemaHall = new CinemaHall(15, "4DX");
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        LocalDateTime firstMovieShowTime = LocalDateTime
                .of(2023, 7, 10, 12,00);
        LocalDateTime secondMovieShowTime = LocalDateTime
                .of(2023, 7, 11, 19,30);
        MovieSession firstMovieSession = new MovieSession(fastAndFurious,
                firstCinemaHall, firstMovieShowTime);
        MovieSession secondMovieSession = new MovieSession(fastAndFurious,
                secondCinemaHall, secondMovieShowTime);

        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        LocalDate date = LocalDate.of(2023, 7, 10);
        movieSessionService.findAvailableSessions(firstMovieSession.getId(), date)
                .forEach(System.out::println);
    }
}
