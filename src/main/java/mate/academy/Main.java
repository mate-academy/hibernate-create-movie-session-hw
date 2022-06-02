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
    private static MovieService movieService = (MovieService)
            injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService = (MovieSessionService)
            injector.getInstance(MovieSessionService.class);
    private static CinemaHallService cinemaHallService = (CinemaHallService)
            injector.getInstance(CinemaHallService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall testCinemaHall = new CinemaHall();
        testCinemaHall.setCapacity(400);
        testCinemaHall.setDescription("Just test cinema hall");
        cinemaHallService.add(testCinemaHall);
        System.out.println(cinemaHallService.get(testCinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession testMovieSession = new MovieSession();
        testMovieSession.setMovie(fastAndFurious);
        testMovieSession.setCinemaHall(testCinemaHall);
        testMovieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(testMovieSession);
        System.out.println(movieSessionService.get(testMovieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
