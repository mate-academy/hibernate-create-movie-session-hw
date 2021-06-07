package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService = (MovieService) injector
            .getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService) injector
            .getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService) injector
            .getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        Movie terminator = new Movie("Terminator");
        terminator.setDescription("Very awesome movie.");
        movieService.add(terminator);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(terminator.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall alfaCinemaHall = new CinemaHall(100, "Big cinema hall "
                + "for top premiere movies");
        cinemaHallService.add(alfaCinemaHall);
        CinemaHall betaCinemaHall = new CinemaHall(50, "Small cinema hall "
                + "for non top premiere movies");
        cinemaHallService.add(betaCinemaHall);
        System.out.println(cinemaHallService.get(alfaCinemaHall.getId()));
        System.out.println(cinemaHallService.get(betaCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession(fastAndFurious, alfaCinemaHall,
                LocalDateTime.of(2021, Month.JUNE, 10, 10, 0, 0));
        movieSessionService.add(firstMovieSession);
        MovieSession secondMovieSession = new MovieSession(fastAndFurious, alfaCinemaHall,
                LocalDateTime.of(2021, Month.JUNE, 10, 15, 0, 0));
        movieSessionService.add(secondMovieSession);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.of(2021,
                Month.JUNE, 10)).forEach(System.out::println);
    }
}
