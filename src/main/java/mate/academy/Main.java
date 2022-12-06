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
        System.out.println("====Movies=====");
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        System.out.println("====CinemaHalls=====");
        CinemaHall hall3D = new CinemaHall();
        hall3D.setCapacity(100);
        hall3D.setDescription("Enjoy movie in 3D!");
        CinemaHall hall4DX = new CinemaHall();
        hall4DX.setCapacity(50);
        hall4DX.setDescription("Much better than 3D :)");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(hall3D);
        cinemaHallService.add(hall4DX);
        cinemaHallService.get(hall3D.getId());
        cinemaHallService.getAll().forEach(System.out::println);

        System.out.println("====MovieSession====");
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(hall4DX);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.get(1L);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now());
    }
}
