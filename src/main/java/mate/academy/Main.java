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
        Movie lordoftheRings = new Movie("The Lord of the Rings");
        lordoftheRings.setDescription("is the saga of a group of sometimes reluctant"
                + " heroes who set forth to save their world from consummate evil. ");
        movieService.add(lordoftheRings);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall firstCinemaHall = new CinemaHall();
        cinemaHallService.add(firstCinemaHall);
        CinemaHall secondCinemaHall = new CinemaHall();
        cinemaHallService.add(secondCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession boringMovieSession = new MovieSession();
        boringMovieSession.setMovie(fastAndFurious);
        boringMovieSession.setCinemaHall(firstCinemaHall);
        boringMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(boringMovieSession);
        MovieSession funMovieSession = new MovieSession();
        funMovieSession.setMovie(lordoftheRings);
        funMovieSession.setCinemaHall(secondCinemaHall);
        funMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(funMovieSession);
        System.out.println(movieSessionService.get(funMovieSession.getId()));
        movieSessionService.findAvailableSessions(lordoftheRings.getId(),
                LocalDate.from(LocalDateTime.now())).forEach(System.out::println);
    }
}
