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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(25);
        cinemaHall.setDescription("Red");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningMovieSession = new MovieSession();
        morningMovieSession.setMovie(fastAndFurious);
        morningMovieSession.setCinemaHall(cinemaHall);
        morningMovieSession
                .setShowTime(LocalDateTime.of(2022, 7, 26, 11, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(morningMovieSession);
        MovieSession lateNightMovieSession = new MovieSession();
        lateNightMovieSession.setMovie(fastAndFurious);
        lateNightMovieSession.setCinemaHall(cinemaHall);
        lateNightMovieSession
                .setShowTime(LocalDateTime.of(2022, 7, 26, 21, 0));
        movieSessionService.add(lateNightMovieSession);
        System.out.println(movieSessionService.get(morningMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                        LocalDate.of(2022, 7, 26))
                .forEach(System.out::println);
    }
}
