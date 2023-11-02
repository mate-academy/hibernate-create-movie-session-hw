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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie kolombo = new Movie("Kolombo");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        kolombo.setDescription("Detektiv");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(kolombo);
        movieService.add(fastAndFurious);
        movieService.get(1L);
        System.out.println(movieService.getAll());
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall2D = new CinemaHall();
        hall2D.setCapacity(20);
        cinemaHallService.add(hall2D);
        System.out.println(cinemaHallService.get(1L));
        MovieSession daySession = new MovieSession();
        daySession.setMovie(fastAndFurious);
        daySession.setCinemaHall(hall2D);
        daySession.setShowTime(LocalDateTime.now().minusDays(1));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(daySession);
        MovieSession nightSession = new MovieSession();
        nightSession.setShowTime(LocalDateTime.now().plusHours(2));
        nightSession.setMovie(kolombo);
        movieSessionService.add(nightSession);
        MovieSession morningSession = new MovieSession();
        morningSession.setShowTime(LocalDateTime.now());
        morningSession.setMovie(kolombo);
        morningSession.setCinemaHall(hall2D);
        movieSessionService.add(morningSession);
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.now()));
    }
}
