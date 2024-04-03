package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");
    public static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    public static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);
    public static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie up = new Movie("Up");
        up.setDescription("cartoons");
        movieService.add(up);
        System.out.println(movieService.get(up.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(250);
        firstCinemaHall.setDescription("Cool cinema hall");
        cinemaHallService.add(firstCinemaHall);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setMovie(fastAndFurious);
        firstMovieSession.setCinemaHall(firstCinemaHall);
        firstMovieSession.setShowTime(
                LocalDateTime.of(2020, 1, 1, 12, 0));
        movieSessionService.add(firstMovieSession);

        MovieSession secondMovieSession = new MovieSession();
        secondMovieSession.setMovie(up);
        secondMovieSession.setCinemaHall(firstCinemaHall);
        secondMovieSession.setShowTime(
                LocalDateTime.of(2020, 1, 2, 12, 0));
        movieSessionService.add(secondMovieSession);

        System.out.println(movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                firstMovieSession.getShowTime().toLocalDate()));
    }
}
