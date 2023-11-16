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
        Injector injector = Injector.getInstance("mate.academy");

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Hall 1");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHall.setCapacity(200);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(1L));
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        LocalDateTime localDateTime =
                LocalDateTime.of(2023, 11, 13, 20, 0);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSession.setShowTime(localDateTime);
        movieSessionService.add(movieSession);
        movieSessionService.get(1L);
        movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2023, 11, 13));
    }
}
