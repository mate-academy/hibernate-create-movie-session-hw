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

    public static void main(String[] args) {
        final MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(90);
        cinemaHall.setDescription("Great hall!");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.getAll());

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2022, 10, 12, 8, 0));
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);
        System.out.println(movieSessionService.get(1L));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setShowTime(LocalDateTime.of(2022, 10, 12, 19, 0));
        movieSession2.setMovie(fastAndFurious);
        movieSession2.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setShowTime(LocalDateTime.of(2022, 10, 12, 19, 0));
        movieSession3.setMovie(movieService.add(new Movie()));
        movieSession3.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(3L));

        movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2022, 10, 12))
                .forEach(System.out::println);
    }
}
