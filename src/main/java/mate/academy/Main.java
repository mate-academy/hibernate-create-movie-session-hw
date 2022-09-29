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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall relux = new CinemaHall();
        relux.setCapacity(50);
        relux.setDescription("This is a luxury cinema hall with comfortable chairs and tables");
        cinemaHallService.add(relux);
        System.out.println(cinemaHallService.get(relux.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fastAndFuriousFilm = new MovieSession();
        fastAndFuriousFilm.setMovie(fastAndFurious);
        fastAndFuriousFilm.setCinemaHall(relux);
        fastAndFuriousFilm.setShowTime(LocalDateTime.of(
                2022, 7, 8, 23, 0, 0));
        movieSessionService.add(fastAndFuriousFilm);
        System.out.println(movieSessionService.get(fastAndFuriousFilm.getId()));
        movieSessionService.findAvailableSessions(fastAndFuriousFilm.getId(),
                LocalDate.of(2022, 7, 8)).forEach(System.out::println);
    }
}
