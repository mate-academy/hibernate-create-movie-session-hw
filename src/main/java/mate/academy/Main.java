package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall.setDescription("First cinema hall");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setMovie(fastAndFurious);
        firstSession.setCinemaHall(cinemaHall);
        firstSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));

        MovieSession secondSession = new MovieSession();
        secondSession.setMovie(fastAndFurious);
        secondSession.setCinemaHall(cinemaHall);
        secondSession.setShowTime(LocalDateTime.of(2021, 5, 6, 13, 00));
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));

        List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
        availableSessions.forEach(System.out::println);
    }
}
