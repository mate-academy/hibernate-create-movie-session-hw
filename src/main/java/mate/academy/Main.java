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
        MovieService movieService
                = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall(10, "description1");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSession1 = new MovieSession(LocalDateTime.now());
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setMovie(fastAndFurious);
        movieSessionService.add(movieSession1);
        MovieSession movieSession2 = new MovieSession(LocalDateTime.now().plusHours(5));
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setMovie(fastAndFurious);
        movieSessionService.add(movieSession2);
        MovieSession movieSession3 = new MovieSession(LocalDateTime.now().plusDays(1));
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setMovie(fastAndFurious);
        movieSessionService.add(movieSession3);
        System.out.println(movieSessionService.get(movieSession2.getId()));
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), LocalDate.now())
                .forEach(System.out::println);
    }
}
