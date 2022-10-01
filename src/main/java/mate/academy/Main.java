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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        System.out.println("---------------------------------------------");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("---------------------------------------------");
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(150);
        redHall.setDescription("Reg big hall.");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("---------------------------------------------");
        MovieSession singleMovieSession = new MovieSession();
        singleMovieSession.setMovie(fastAndFurious);
        singleMovieSession.setCinemaHall(redHall);
        singleMovieSession.setShowTime(LocalDateTime.of(2022, 10, 30,18, 0));
        movieSessionService.add(singleMovieSession);
        System.out.println(movieSessionService.get(singleMovieSession.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2022, 10, 30))
                .forEach(System.out::println);
    }
}
