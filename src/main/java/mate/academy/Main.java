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
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(50);
        firstCinemaHall.setDescription("Dolby atmos sound system");
        cinemaHallService.add(firstCinemaHall);
        System.out.println(cinemaHallService.get(firstCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession morningMovieSession = new MovieSession();
        morningMovieSession.setMovie(fastAndFurious);
        morningMovieSession.setCinemaHall(firstCinemaHall);
        LocalDateTime showTime = LocalDateTime.of(2023, 6, 16, 9, 30);
        morningMovieSession.setShowTime(showTime);

        movieSessionService.add(morningMovieSession);
        System.out.println(movieSessionService.get(morningMovieSession.getId()));
        LocalDate date = LocalDate.of(2023, 6, 16);
        movieSessionService.findAvailableSessions(morningMovieSession.getId(), date)
                .forEach(System.out::println);
    }
}
