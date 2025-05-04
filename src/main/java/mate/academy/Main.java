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
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.getAll().forEach(System.out::println);

        CinemaHall purpleHall = new CinemaHall();
        purpleHall.setCapacity(120);
        purpleHall.setDescription("Purple hall with 120 seats");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(purpleHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession fafAtPurpleHall = new MovieSession();
        fafAtPurpleHall.setCinemaHall(purpleHall);
        fafAtPurpleHall.setMovie(fastAndFurious);
        fafAtPurpleHall.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(fafAtPurpleHall);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}
