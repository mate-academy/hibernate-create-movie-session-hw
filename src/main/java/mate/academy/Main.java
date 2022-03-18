package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService = (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall redHall = new CinemaHall();
        redHall.setCapacity(55);
        redHall.setDescription("large hall with soft armchairs");
        cinemaHallService.add(redHall);
        System.out.println(cinemaHallService.get(redHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService = (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession movieSessionOnFriday = new MovieSession();
        movieSessionOnFriday.setMovie(fastAndFurious);
        movieSessionOnFriday.setCinemaHall(redHall);
        movieSessionOnFriday.setShowTime(LocalDateTime.of(2022, 3, 25, 20, 0));
        System.out.println(movieSessionService.add(movieSessionOnFriday));
        System.out.println(movieSessionService.get(movieSessionOnFriday.getId()));
        MovieSession movieSessionOnMonday = new MovieSession();
        movieSessionOnMonday.setMovie(fastAndFurious);
        movieSessionOnMonday.setCinemaHall(redHall);
        movieSessionOnMonday.setShowTime(LocalDateTime.of(2022, 3, 21, 15, 0));
        System.out.println(movieSessionService.add(movieSessionOnMonday));
        System.out.println(movieSessionService.get(movieSessionOnMonday.getId()));
        System.out.println(System.lineSeparator());
        System.out.println(movieSessionService.findAvailableSessions(1L, LocalDate.of(2022, 3, 25)));
    }
}
