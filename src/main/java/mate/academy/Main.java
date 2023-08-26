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
        Movie jumanji = new Movie("Jumanji");
        jumanji.setDescription("The story centers on a supernatural board game that releases "
                + "jungle-based hazards upon its players with every turn they take.");
        movieService.add(jumanji);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall smallCinemaHall = new CinemaHall(60, "Small hall");
        cinemaHallService.add(smallCinemaHall);
        CinemaHall bigCinemaHall = new CinemaHall(120, "Big hall");
        cinemaHallService.add(bigCinemaHall);
        System.out.println(cinemaHallService.get(smallCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession boringMovieSession = new MovieSession();
        boringMovieSession.setMovie(fastAndFurious);
        boringMovieSession.setCinemaHall(smallCinemaHall);
        boringMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(boringMovieSession);
        MovieSession funMovieSession = new MovieSession();
        funMovieSession.setMovie(jumanji);
        funMovieSession.setCinemaHall(bigCinemaHall);
        funMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(funMovieSession);
        System.out.println(movieSessionService.get(funMovieSession.getId()));
        movieSessionService.findAvailableSessions(jumanji.getId(),
                LocalDate.from(LocalDateTime.now())).forEach(System.out::println);
    }
}
