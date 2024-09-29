package mate.academy;

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
        System.out.println("Movie services below");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("Cinema services below");
        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall imax = new CinemaHall();
        imax.setCapacity(50);
        imax.setDescription("IMAX 3D cinema hall");
        cinemaHallService.add(imax);
        System.out.println(cinemaHallService.get(imax.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("Movie session services below");
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(imax);
        movieSession1.setShowTime(LocalDateTime.of(2024, 5, 22, 10, 30, 40));
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.findAvailableSessions(movieSession1.getId(),
                movieSession1.getShowTime().toLocalDate()));
    }
}
