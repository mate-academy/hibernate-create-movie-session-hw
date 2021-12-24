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
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        CinemaHall yellowHall = new CinemaHall();
        yellowHall.setCapacity(220);
        yellowHall.setDescription("Big yellow hall");
        cinemaHallService.add(yellowHall);
        CinemaHall purpleHall = new CinemaHall();
        purpleHall.setCapacity(70);
        purpleHall.setDescription("Small purple hall");
        cinemaHallService.add(purpleHall);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        Movie spiderMan = new Movie("Spider-Man: No Way Home");
        fastAndFurious.setDescription("Parker asks Dr. Stephen Strange to make his "
                 + "identity as Spider-Man a secret again...");
        movieService.add(spiderMan);
        System.out.println(movieService.get(spiderMan.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession morningSession = new MovieSession();
        morningSession.setMovie(fastAndFurious);
        morningSession.setCinemaHall(yellowHall);
        morningSession.setShowTime(LocalDateTime.of(2021, 12, 25, 10, 10));
        movieSessionService.add(morningSession);

        MovieSession afternoonSession = new MovieSession();
        afternoonSession.setMovie(spiderMan);
        afternoonSession.setCinemaHall(purpleHall);
        afternoonSession.setShowTime(LocalDateTime.of(2021, 12, 25, 13, 10));
        movieSessionService.add(afternoonSession);

        System.out.println(cinemaHallService.get(yellowHall.getId()));
        System.out.println(cinemaHallService.get(purpleHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(spiderMan.getId()));
        movieService.getAll().forEach(System.out::println);

        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(spiderMan.getId(), LocalDate.of(2021, 12, 25));
        availableSessions.forEach(System.out::println);
    }
}
