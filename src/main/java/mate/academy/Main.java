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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallFirst = new CinemaHall();
        cinemaHallFirst.setCapacity(60);
        cinemaHallFirst.setDescription("First cinema hall");
        cinemaHallService.add(cinemaHallFirst);
        CinemaHall cinemaHallSecond = new CinemaHall();
        cinemaHallSecond.setCapacity(90);
        cinemaHallSecond.setDescription("Second cinema hall");
        cinemaHallService.add(cinemaHallSecond);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHallSecond);
        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 7, 14,45);
        movieSession.setShowTime(localDateTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(1L));
        LocalDate localDate = LocalDate.of(2023, 7, 7);
        movieSessionService.findAvailableSessions(1L, localDate).forEach(System.out::println);
    }
}
