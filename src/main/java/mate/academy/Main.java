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

    public static void main(String[] args) {
        Movie fastAndFurious = new Movie();
        fastAndFurious.setTitle("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("-=-=-=-=-");

        CinemaHall twoDimensionalCinemaHall = new CinemaHall();
        twoDimensionalCinemaHall.setCapacity(100);
        twoDimensionalCinemaHall.setDescription("Planet of cinema. 2D hall");
        CinemaHall threeDimensionalCinemaHall = new CinemaHall();
        threeDimensionalCinemaHall.setCapacity(200);
        threeDimensionalCinemaHall.setDescription("Planet of cinema. 3D hall");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(twoDimensionalCinemaHall);
        cinemaHallService.add(threeDimensionalCinemaHall);
        System.out.println(cinemaHallService.get(twoDimensionalCinemaHall.getId()));
        System.out.println(cinemaHallService.getAll());

        System.out.println("-=-=-=-=-");

        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(threeDimensionalCinemaHall);
        movieSession.setShowTime(date);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService
                .findAvailableSessions(movieSession.getId(), LocalDate.now()));
    }
}
