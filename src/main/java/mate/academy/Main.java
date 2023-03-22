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

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie terminator = new Movie("Terminator");
        terminator.setDescription("I'll be back");
        movieService.add(terminator);
        System.out.println(movieService.get(terminator.getId()));

        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall redCinemaHall = new CinemaHall(100, "red");
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(redCinemaHall.getId()));

        CinemaHall greenCinemaHall = new CinemaHall(200, "green");
        cinemaHallService.add(greenCinemaHall);
        System.out.println(cinemaHallService.get(greenCinemaHall.getId()));

        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession redMovieSession = new MovieSession();
        redMovieSession.setMovie(fastAndFurious);
        redMovieSession.setCinemaHall(redCinemaHall);
        redMovieSession.setShowDate(LocalDateTime.now());

        MovieSession greenMovieSession = new MovieSession();
        greenMovieSession.setMovie(terminator);
        greenMovieSession.setCinemaHall(greenCinemaHall);
        greenMovieSession.setShowDate(LocalDateTime.now());

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        movieSessionService.add(redMovieSession);
        movieSessionService.add(greenMovieSession);

        System.out.println(movieSessionService.get(redMovieSession.getId()));
        System.out.println(movieSessionService.get(greenMovieSession.getId()));

        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        movieSessionService.findAvailableSessions(terminator.getId(), LocalDate.now());
    }
}
