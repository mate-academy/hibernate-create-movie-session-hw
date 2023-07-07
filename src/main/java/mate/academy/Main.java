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
        MovieService movieService = (MovieService)
                injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println(movieService.get(fastAndFurious.getId()));

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall firstCinemaHall =
                new CinemaHall(30, "2D");
        CinemaHall secondCinemaHall =
                new CinemaHall(45, "3D");
        cinemaHallService.add(secondCinemaHall);
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        System.out.println(cinemaHallService.get(secondCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);

        LocalDateTime firstMovieTime = LocalDateTime
                .of(2023, 5, 6, 7,58);
        LocalDateTime lastMovieTime = LocalDateTime
                .of(2023, 8, 9, 10,50);
        MovieSession firstMovieSession = new MovieSession(fastAndFurious,
                firstCinemaHall, firstMovieTime);
        MovieSession secondMovieSession = new MovieSession(fastAndFurious,
                secondCinemaHall, lastMovieTime);

        movieSessionService.add(firstMovieSession);
        movieSessionService.add(secondMovieSession);
        System.out.println(movieSessionService.get(firstMovieSession.getId()));
        System.out.println(movieSessionService.get(secondMovieSession.getId()));
        movieSessionService.findAvailableSessions(firstMovieSession.getId(),
                LocalDate.of(2023, 5, 6)).forEach(System.out::println);
    }
}
