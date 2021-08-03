package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    private static CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie("Fast and Furious");
        Movie avengers = new Movie("Avengers");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        avengers.setDescription("New awesome film from marvel studio");
        movieService.add(fastAndFurious);
        movieService.add(avengers);
        System.out.println(movieService.get(fastAndFurious.getId()));
        System.out.println(movieService.get(avengers.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(150);
        redHall.setDescription("Newly equipped cinema hall with capacity 150 persons");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(redHall);
        LocalDateTime sessionTime = LocalDateTime.of(2021, Month.JULY, 4, 23, 10);
        movieSession.setShowTime(sessionTime);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        LocalDate date = LocalDate.of(2021, Month.JULY, 4);
        movieSessionService.findAvailableSessions(1L, date).forEach(System.out::println);
    }
}
