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
    private static MovieService movieService;
    private static CinemaHallService cinemaHallService;
    private static MovieSessionService movieSessionService;

    public static void main(String[] args) {
        movieService = (MovieService) injector.getInstance(MovieService.class);
        cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstHall = new CinemaHall();
        firstHall.setCapacity(50);
        firstHall.setDecription("Modern hall with capacity 50 seats");
        cinemaHallService.add(firstHall);
        System.out.println(cinemaHallService.get(firstHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(firstHall);
        firstSession.setMovie(fastAndFurious);
        firstSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(firstSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(),
                LocalDate.from(LocalDateTime.now())).forEach(System.out::println);
    }
}
