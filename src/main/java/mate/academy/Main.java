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
    public static void main(String[] args) {
        // MovieService test
        System.out.println(" - MovieService test -");

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        Movie terminator = new Movie("Terminator");
        terminator.setDescription("A SciFi film about evil robots and time travel.");

        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        movieService.add(terminator);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(terminator.getId()));
        movieService.getAll().forEach(System.out::println);

        // CinemaHallService test
        System.out.println(" - CinemaHallService test -");

        CinemaHall cinemaHall3d = new CinemaHall();
        cinemaHall3d.setCapacity(50);
        cinemaHall3d.setDescription("3D Dolby Surround Water Sprinkler Wind Blower");

        CinemaHall cinemaHall2d = new CinemaHall();
        cinemaHall2d.setCapacity(100);
        cinemaHall2d.setDescription("An Average 2D Cinema Hall");

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall3d);
        cinemaHallService.add(cinemaHall2d);

        System.out.println(cinemaHallService.get(cinemaHall3d.getId()));
        System.out.println(cinemaHallService.get(cinemaHall2d.getId()));
        System.out.println(cinemaHallService.getAll());

        // MovieSessionService test
        System.out.println(" - MovieSessionService test -");

        MovieSession movieSessionFastAndFurious = new MovieSession();
        movieSessionFastAndFurious.setMovie(fastAndFurious);
        movieSessionFastAndFurious.setCinemaHall(cinemaHall3d);
        movieSessionFastAndFurious.setShowTime(LocalDateTime.of(
                2022, 8, 14, 16, 0, 0));

        MovieSession movieSessionTerminator = new MovieSession();
        movieSessionTerminator.setMovie(terminator);
        movieSessionTerminator.setCinemaHall(cinemaHall3d);
        movieSessionTerminator.setShowTime(LocalDateTime.of(
                2022, 8, 14, 11, 0, 0));

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSessionFastAndFurious);
        movieSessionService.add(movieSessionTerminator);

        System.out.println(movieSessionService.get(movieSessionFastAndFurious.getId()));
        System.out.println(movieSessionService.get(movieSessionTerminator.getId()));
        System.out.println(movieSessionService.findAvailableSessions(
                movieSessionFastAndFurious.getId(),
                LocalDate.of(2022, 8, 14)));
        System.out.println(movieSessionService.findAvailableSessions(
                movieSessionTerminator.getId(),
                LocalDate.of(2022, 8, 14)));
    }
}
