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
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        MovieService movieService =
                (MovieService) injector.getInstance(MovieService.class);
        movieService.add(fastAndFurious);
        System.out.println(movieService.get(1L));
        movieService.getAll().forEach(System.out::println);

        CinemaHall mainHall = new CinemaHall();
        mainHall.setCapacity(100);
        mainHall.setDescription("The best hall in our cinema");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        cinemaHallService.add(mainHall);
        System.out.println(cinemaHallService.get(1L));
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstSession = new MovieSession();
        firstSession.setCinemaHall(cinemaHallService.get(1L));
        firstSession.setMovie(movieService.get(1L));
        firstSession.setShowTime(LocalDateTime
                .of(2021, 12, 31, 19, 0, 0));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(firstSession);
        System.out.println(movieSessionService.get(1L));
        movieSessionService.findAvailableSessions(1L, LocalDate.of(2021, 12, 31))
                .forEach(System.out::println);
    }
}
