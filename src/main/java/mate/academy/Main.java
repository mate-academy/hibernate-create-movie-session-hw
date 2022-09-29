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
    private static final Injector injector =
            Injector.getInstance("mate.academy");
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
        movieService.get(fastAndFurious.getId());
        movieService.getAll().forEach(System.out::println);

        CinemaHall imaxHall = new CinemaHall();
        imaxHall.setCapacity(80);
        imaxHall.setDescription("This is a new imax hall with 80 seats");
        cinemaHallService.add(imaxHall);
        cinemaHallService.get(imaxHall.getId());
        cinemaHallService.getAll();

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setCinemaHall(imaxHall);
        movieSessionService.add(movieSession);
        movieSessionService.get(movieSession.getId());
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
