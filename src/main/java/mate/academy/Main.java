package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static final String PACKAGE_NAME = "mate.academy";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE_NAME);

        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("cinemaHall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(movieService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(cinemaHall);
        session1.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(fastAndFurious);
        session2.setCinemaHall(cinemaHall);
        session2.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusDays(1));
        movieSessionService.add(session2);
        System.out.println(movieSessionService.get(session2.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
