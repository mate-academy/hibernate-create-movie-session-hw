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
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall1 = new CinemaHall(120, "First cinema hall");
        cinemaHallService.add(cinemaHall1);
        System.out.println(cinemaHallService.get(cinemaHall1.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession movieSessionNow = new
                MovieSession(LocalDateTime.now(), fastAndFurious, cinemaHall1);
        movieSessionService.add(movieSessionNow);
        System.out.println(movieSessionService.get(movieSessionNow.getId()));
        MovieSession movieSessionOld = new MovieSession(
                LocalDateTime.of(2000, 1, 1, 0, 0, 0), fastAndFurious, cinemaHall1);
        movieSessionService.add(movieSessionOld);
        MovieSession movieSessionLater =
                new MovieSession(LocalDateTime.now().plusHours(1), fastAndFurious, cinemaHall1);
        movieSessionService.add(movieSessionLater);
        MovieSession movieSessionEarlier =
                new MovieSession(LocalDateTime.now().minusHours(1), fastAndFurious, cinemaHall1);
        movieSessionService.add(movieSessionEarlier);
        movieSessionService.findAvailableSessions(
                fastAndFurious.getId(), LocalDate.now()).forEach(System.out::println);
    }
}
