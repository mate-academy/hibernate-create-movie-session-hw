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
    public static void main(String[] args) {
        final Injector injector = Injector.getInstance("mate.academy");

        final MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHallOne = new CinemaHall();
        cinemaHallOne.setCapacity(50);
        cinemaHallOne.setDescription("Middle Capacity Hall");
        cinemaHallService.add(cinemaHallOne);
        cinemaHallService.get(1L);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSessionOne = new MovieSession();
        movieSessionOne.setMovie(fastAndFurious);
        movieSessionOne.setCinemaHall(cinemaHallOne);
        movieSessionOne.setLocalDateTime(LocalDateTime.now());
        movieSessionService.add(movieSessionOne);
        movieSessionService.get(1L);
        movieSessionService.findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
