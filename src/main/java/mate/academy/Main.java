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
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(12);
        cinemaHall.setDescription("hello");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setMovie(movieService.get(fastAndFurious.getId()));
        movieSession.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));
        MovieSession movieSession2 = new MovieSession();
        movieSession2.setShowTime(LocalDateTime.now().plusDays(1));
        movieSession2.setMovie(movieService.get(fastAndFurious.getId()));
        movieSession2.setCinemaHall(cinemaHallService.get(cinemaHall.getId()));

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.add(movieSession2);
        System.out.println(movieSessionService.get(movieSession2.getId()));
        System.out.println(movieSessionService
                .findAvailableSessions(1L, LocalDate.of(2022, 12, 6)));
    }
}
