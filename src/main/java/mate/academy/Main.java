package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("4DX");
        cinemaHallService.add(cinemaHall);
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.of(2022, Month.JULY, 19, 20, 15));
        movieSession.setMovie(fastAndFurious);
        movieSession.setCinemaHall(cinemaHall);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        MovieSession movieSession1 = new MovieSession();
        movieSession1.setShowTime(LocalDateTime.of(2022, Month.JULY, 19, 22, 15));
        movieSession1.setMovie(fastAndFurious);
        movieSession1.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession1);
        System.out.println(movieSessionService.get(movieSession.getId()));
        movieSessionService
                .findAvailableSessions(
                        fastAndFurious.getId(), LocalDate.of(
                                2022, Month.JULY, 19))
                .forEach(System.out::println);
    }
}
