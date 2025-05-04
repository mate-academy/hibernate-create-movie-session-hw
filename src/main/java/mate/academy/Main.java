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

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie putinDied = new Movie("How putin died in agony");
        putinDied.setDescription("The best movie of 2022.");
        movieService.add(putinDied);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall blueCinemaHall = new CinemaHall(60, "Small hall with comfortable seats");
        cinemaHallService.add(blueCinemaHall);
        CinemaHall redCinemaHall = new CinemaHall(80, "Big hall");
        cinemaHallService.add(redCinemaHall);
        System.out.println(cinemaHallService.get(blueCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession boringMovieSession = new MovieSession();
        boringMovieSession.setMovie(fastAndFurious);
        boringMovieSession.setCinemaHall(blueCinemaHall);
        boringMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(boringMovieSession);
        MovieSession funMovieSession = new MovieSession();
        funMovieSession.setMovie(putinDied);
        funMovieSession.setCinemaHall(redCinemaHall);
        funMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(funMovieSession);
        System.out.println(movieSessionService.get(funMovieSession.getId()));
        movieSessionService.findAvailableSessions(putinDied.getId(),
                LocalDate.from(LocalDateTime.now())).forEach(System.out::println);

    }
}
