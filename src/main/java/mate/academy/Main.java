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
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final MovieService movieService
            = (MovieService) injector.getInstance(MovieService.class);
    private static final CinemaHallService cinemaHallService
            = (CinemaHallService) injector.getInstance(CinemaHallService.class);
    private static final MovieSessionService movieSessionService
            = (MovieSessionService) injector.getInstance(MovieSessionService.class);

    public static void main(String[] args) {

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);
        System.out.println("----Start test MovieService----");
        System.out.println(movieService.get(fastAndFurious.getId()));
        movieService.getAll().forEach(System.out::println);
        System.out.println("----End test MovieService----");

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription("Cool hall");
        cinemaHall.setCapacity(100);
        cinemaHallService.add(cinemaHall);
        System.out.println("----Start test MovieService----");
        System.out.println(cinemaHallService.get(cinemaHall.getId()));
        cinemaHallService.getAll().forEach(System.out::println);
        System.out.println("----End test MovieService----");

        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(fastAndFurious);
        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 12, 12, 12, 12);
        movieSession.setShowTime(localDateTime);
        movieSession.setCinemaHall(cinemaHall);
        movieSessionService.add(movieSession);
        System.out.println("----Start test MovieService----");
        System.out.println(movieSessionService.get(movieSession.getId()));
        LocalDate localDate = localDateTime.toLocalDate();
        movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), localDate)
                .forEach(System.out::println);
        System.out.println("----End test MovieService----");
    }
}
