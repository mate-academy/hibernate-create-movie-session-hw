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
    private static final Injector serviceInjector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) serviceInjector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) serviceInjector.getInstance(CinemaHallService.class);
        CinemaHall luxCinemaHall = new CinemaHall(250);
        luxCinemaHall.setDescription("Lux cinema hall");
        cinemaHallService.add(luxCinemaHall);

        MovieSession middleTimeMovieSession =
                new MovieSession(LocalDateTime.of(2023, 9, 16, 16, 30, 00));
        middleTimeMovieSession.setMovie(fastAndFurious);
        middleTimeMovieSession.setCinemaHall(luxCinemaHall);
        middleTimeMovieSession.setMovie(fastAndFurious);
        MovieSessionService movieSessionService =
                (MovieSessionService) serviceInjector.getInstance(MovieSessionService.class);
        movieSessionService.add(middleTimeMovieSession);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(cinemaHallService.get(luxCinemaHall.getId()));
        System.out.println(movieSessionService.findAvailableSessions(middleTimeMovieSession.getId(),
                LocalDate.of(2023, 9, 16)));
        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);
        movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2023, 9, 16)).forEach(System.out::println);
    }
}
