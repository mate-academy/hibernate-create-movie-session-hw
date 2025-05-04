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
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final CinemaHallService cinemaHallService =
            (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieService movieService =
            (MovieService) injector.getInstance(MovieService.class);
    private static final MovieSessionService movieSessionService =
            (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHall twoDimensionalCinemaHall = new CinemaHall();
        twoDimensionalCinemaHall.setCapacity(100);
        twoDimensionalCinemaHall.setDescription("2D hall");
        CinemaHall threeDimensionalCinemaHall = new CinemaHall();
        threeDimensionalCinemaHall.setCapacity(200);
        threeDimensionalCinemaHall.setDescription("3D hall");
        CinemaHall sevenDimensionalCinemaHall = new CinemaHall();
        sevenDimensionalCinemaHall.setCapacity(20);
        sevenDimensionalCinemaHall.setDescription("wou 7D hall!");
        cinemaHallService.add(twoDimensionalCinemaHall);
        cinemaHallService.add(threeDimensionalCinemaHall);
        cinemaHallService.add(sevenDimensionalCinemaHall);
        System.out.println(cinemaHallService.get(sevenDimensionalCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(sevenDimensionalCinemaHall);
        movieSession.setShowTime(date);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
