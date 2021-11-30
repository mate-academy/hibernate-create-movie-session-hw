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
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall luxHall = new CinemaHall();
        luxHall.setCapacity(10);
        luxHall.setDescription("4D");
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(luxHall);
        System.out.println(cinemaHallService.get(luxHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession availableFastAndFuriousMovieSession = new MovieSession();
        availableFastAndFuriousMovieSession.setMovie(fastAndFurious);
        availableFastAndFuriousMovieSession.setCinemaHall(luxHall);
        availableFastAndFuriousMovieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(availableFastAndFuriousMovieSession);
        System.out.println(movieSessionService.get(availableFastAndFuriousMovieSession.getId()));
        MovieSession unavailableFastAndFuriousMovieSession = new MovieSession();
        unavailableFastAndFuriousMovieSession.setMovie(fastAndFurious);
        unavailableFastAndFuriousMovieSession.setCinemaHall(luxHall);
        unavailableFastAndFuriousMovieSession.setShowTime(LocalDateTime.of(2021, 10, 25,
                10, 30, 12));
        movieSessionService.add(unavailableFastAndFuriousMovieSession);
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 10, 26))
                .forEach(System.out::println);
    }
}
