package mate.academy;

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
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        //MovieService
        final MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        //CinemaHallService
        final CinemaHallService cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        CinemaHall hall1 = new CinemaHall();
        hall1.setCapacity(100);
        hall1.setDescription("description of hall 1");
        CinemaHall hall2 = new CinemaHall();
        hall2.setCapacity(200);
        hall2.setDescription("description of hall 2");
        cinemaHallService.add(hall1);
        cinemaHallService.add(hall2);
        System.out.println(cinemaHallService.get(hall1.getId()));
        System.out.println(cinemaHallService.get(hall2.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        //MovieSessionService
        final MovieSessionService movieSessionService
                = (MovieSessionService) INJECTOR.getInstance(MovieSessionService.class);
        final MovieSession movieSession1 = new MovieSession();
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(hall1);
        final LocalDateTime localDateTime1 = LocalDateTime.parse("2023-10-25T12:00:00");
        final LocalDateTime localDateTime2 = LocalDateTime.parse("2023-10-26T10:00:00");
        final LocalDateTime localDateTime3 = LocalDateTime.parse("2023-10-26T14:00:00");
        movieSession1.setShowTime(localDateTime1);
        final MovieSession movieSession2 = new MovieSession();
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(hall2);
        movieSession2.setShowTime(localDateTime2);
        final MovieSession movieSession3 = new MovieSession();
        movieSession3.setMovie(fastAndFurious);
        movieSession3.setCinemaHall(hall2);
        movieSession3.setShowTime(localDateTime3);
        movieSessionService.add(movieSession1);
        movieSessionService.add(movieSession2);
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession1.getId()));
        System.out.println(movieSessionService.get(movieSession2.getId()));
        final List<MovieSession> availableSessions
                = movieSessionService.findAvailableSessions(1L, localDateTime2.toLocalDate());
        System.out.println(availableSessions);
    }
}
