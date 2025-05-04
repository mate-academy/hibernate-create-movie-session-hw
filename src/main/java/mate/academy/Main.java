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
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        // Movie:
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));

        Movie blackList = new Movie("BlackList");
        blackList.setDescription("It's a serial.");
        movieService.add(blackList);
        System.out.println(movieService.get(blackList.getId()));
        movieService.getAll().forEach(System.out::println);

        // CinemaHall
        CinemaHall hall3D = new CinemaHall();
        hall3D.setCapacity(25);
        hall3D.setDescription("3D hall");
        cinemaHallService.add(hall3D);
        System.out.println(cinemaHallService.get(hall3D.getId()));

        CinemaHall hall2D = new CinemaHall();
        hall2D.setCapacity(50);
        hall2D.setDescription("2D hall");
        cinemaHallService.add(hall2D);
        System.out.println(cinemaHallService.get(hall2D.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        // MovieSession
        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(hall3D);
        firstSession.setMovie(fastAndFurious);
        firstSession.setLocalDateTime(LocalDateTime.of(2021, 10, 23, 20, 30));
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));

        MovieSession secondSession = new MovieSession();
        secondSession.setCinemaHall(hall2D);
        secondSession.setMovie(blackList);
        secondSession.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(secondSession);
        System.out.println(movieSessionService.get(secondSession.getId()));

        System.out.println("AvailableSessions -> \n"
                + movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.now()));
    }
}
